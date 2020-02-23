package github.xuqk.kdtablayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.*
import android.widget.OverScroller
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.KDBaseTab
import kotlin.math.abs


/**
 * Created By：XuQK
 * Created Date：2/15/20 7:39 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class KDTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), KDViewPagerHelper.ViewPageStateListener {

    companion object {
        /**
         * Indicates that the pager is in an idle, settled state. The current page
         * is fully in view and no animation is in progress.
         */
        const val SCROLL_STATE_IDLE = 0

        /**
         * Indicates that the pager is currently being dragged by the user.
         */
        const val SCROLL_STATE_DRAGGING = 1

        /**
         * Indicates that the pager is in the process of settling to a final position.
         */
        const val SCROLL_STATE_SETTLING = 2

        private const val DEFAULT_DURATION: Long = 250

        /**
         * tab可滚动，此时tab的排列为按照tab本身宽度横向依序排列
         */
        const val TAB_MODE_SCROLLABLE = 0
        /**
         * tab不可滚动，此时tab的排列为按照tab本身宽度，以TabLayout的x中线对齐
         */
        const val TAB_MODE_PACK = 1
        /**
         * tab不可滚动，此时tab按比例均分整个TabLayout
         */
        const val TAB_MODE_SPREAD = 2
        /**
         * 自适应模式
         * 所有tab原本总宽度如果没有超过TabLayout，就按MODE_TAB_SPREAD模式排列，
         * 若超过TabLayout总宽度，则按照MODE_TAB_SCROLLABLE排列
         */
        const val TAB_MODE_FLEXIBLE = 3
    }

    // 所有子Tab宽度之和
    private var totalWidth: Int = 0
    private var scrollable: Boolean = false

    // 由于Tab只管横向滑动，所以滑动只记录x坐标即可
    private var lastX: Float = 0f

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private val overScrollDistance = github.xuqk.kdtablayout.dpToPx(context, 16f)
    private val scroller = OverScroller(context)
    private var velocityTracker: VelocityTracker? = null
    private val minVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity

    /**
     * 滚动时，被选中Tab与控件中心x坐标的偏差
     */
    var scrollBiasX: Float = 0f
        set(value) {
            field = github.xuqk.kdtablayout.dpToPx(context, value).toFloat()
        }
    var tabMode: Int =
        TAB_MODE_FLEXIBLE
    var contentAdapter: github.xuqk.kdtablayout.KDTabAdapter? = null
        set(value) {
            field = value
            init()
        }

    var currentItem: Int = 0
        private set

    private var indicator: KDTabIndicator? = null
    private var tabChangeAnimator: ValueAnimator? = null

    private val vpHelper: KDViewPagerHelper by lazy { KDViewPagerHelper() }

    private var scrollState: Int =
        SCROLL_STATE_IDLE

    fun setViewPager(viewPager: ViewPager) {
        vpHelper.bindViewPager(viewPager)
        vpHelper.stateListener = this
    }

    fun setViewPager2(viewPager2: ViewPager2) {
        vpHelper.bindViewPager2(viewPager2)
        vpHelper.stateListener = this
    }

    fun setCurrentItem(position: Int, smooth: Boolean = true) {
        if (currentItem == position || scrollState != SCROLL_STATE_IDLE) return

        val startItem = currentItem

        val view = getChildAt(startItem)
        val destX = getTabScrollXInCenter(view)

        if (smooth) {
            smoothScrollToItem(position)
        } else {
            scrollTo(destX)
            updateTabState()
        }

        currentItem = position
    }

    private fun smoothScrollToItem(position: Int) {
        val startTab = getChildAt(currentItem)
        val endTab = getChildAt(position)

        val startScrollX = scrollX
        tabChangeAnimator?.cancel()
        tabChangeAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration =
                DEFAULT_DURATION
            interpolator = LinearOutSlowInInterpolator()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    scrollState =
                        SCROLL_STATE_IDLE
                }

                override fun onAnimationCancel(animation: Animator?) {
                    scrollState =
                        SCROLL_STATE_IDLE
                }

                override fun onAnimationStart(animation: Animator?) {
                    scrollState =
                        SCROLL_STATE_SETTLING
                }
            })

            addUpdateListener {
                if (it.animatedFraction < 1f) {
                    // 同步tab状态
                    syncTabScrollState(startTab, endTab, it.animatedFraction)

                    // 同步tabLayout scrollX
                    val endScrollX = getTabScrollXInCenter(endTab)
                    scrollTo(startScrollX + ((endScrollX - startScrollX) * it.animatedFraction).toInt())

                    // 同步indicator状态
                    syncIndicatorScrollState(currentItem, position, it.animatedFraction)
                } else {
                    updateTabState()
                }
            }
            start()
        }
    }

    private fun init() {
        contentAdapter?.let { adapter ->
            if (adapter.getTabCount() <= 0) {
                throw IllegalArgumentException("数量必须大于0")
            }
            removeAllViews()
            (0 until adapter.getTabCount()).forEach { i ->
                adapter.createTab(i)?.let {
                    if (i == currentItem) {
                        it.selectTab()
                    } else {
                        it.reset()
                    }
                    addView(it as View)
                }
            }
            indicator = adapter.createIndicator()
            if (indicator != null) {
                setWillNotDraw(false)
            }

            if (childCount > 0 && indicator != null) {
                // tab和indicator都有
            } else if (childCount == 0 && indicator != null) {
                // 只有indicator
            } else if (childCount > 0 && indicator == null) {
                // 只有tab
            } else {
                throw IllegalArgumentException("tab和indicator不能都不设置")
            }

            indicator?.init()
        }
    }

    override fun onDraw(canvas: Canvas) {
        indicator?.draw(canvas)
    }

    fun smoothScrollBy(x: Int) {
        if (!scrollable) return
        scroller.startScroll(scrollX, 0, x, 0, DEFAULT_DURATION.toInt())
        postInvalidateOnAnimation()
    }

    fun smoothScrollTo(destX: Int) {
        if (!scrollable) return
        val dx = when {
            destX < 0 -> -scrollX
            destX > totalWidth - width -> totalWidth - width - scrollX
            else -> destX - scrollX
        }
        scroller.startScroll(scrollX, 0, dx, 0, DEFAULT_DURATION.toInt())
        postInvalidateOnAnimation()
    }

    private fun scrollTo(destX: Int) {
        if (!scrollable) return
        val dx = when {
            destX < 0 -> 0
            destX > totalWidth - width -> totalWidth - width
            else -> destX
        }

        scrollTo(dx, 0)
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, 0)
            postInvalidate()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.rawX
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val evRawX = ev.rawX
                if (abs(evRawX - lastX) > touchSlop) {
                    return true
                }
                lastX = evRawX
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!scrollable) return true

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.addMovement(event)

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val evRawX = event.rawX
                val deltaX = (lastX - evRawX).toInt()
                val resultX = deltaX + scrollX
                when {
                    resultX < 0 -> scrollTo(0, 0)
                    resultX > totalWidth - width -> scrollTo(totalWidth - width, 0)
                    else -> scrollBy(deltaX, 0)
                }
                lastX = evRawX
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (scrollX > 0 && scrollX < totalWidth - width) {
                    velocityTracker?.let {
                        it.computeCurrentVelocity(1000)
                        val xVelocity = it.xVelocity
                        if (abs(xVelocity) > minVelocity) {
                            scroller.fling(
                                scrollX,
                                0,
                                -xVelocity.toInt(),
                                0,
                                0,
                                totalWidth - width,
                                0,
                                0,
                                overScrollDistance,
                                0
                            )
                            postInvalidateOnAnimation()
                        }
                    }
                }

                velocityTracker?.clear()
                velocityTracker?.recycle()
                velocityTracker = null
            }
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        totalWidth = 0
        if (childCount == 0) {
            // 没有tab的情况，TabLayout内容宽度与布局宽度一致
            totalWidth = (indicator?.getWidth() ?: 0) * (contentAdapter?.getTabCount() ?: 0)
        } else {
            var totalWeight = 0
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                measureChild(child, widthMeasureSpec, heightMeasureSpec)
                totalWidth += child.measuredWidth
                totalWeight += (child as KDBaseTab).weight
            }

            when (tabMode) {
                // tab宽度保持既定不变的情况
                TAB_MODE_SCROLLABLE, TAB_MODE_PACK -> {
                    setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
                }
                // tab宽度要按比例占满整个TabLayout的情况
                TAB_MODE_SPREAD -> {
                    totalWidth = measuredWidth
                    for (i in 0 until childCount) {
                        val child = getChildAt(i)
                        child.measure(
                            MeasureSpec.makeMeasureSpec(
                                totalWidth * (child as KDBaseTab).weight / totalWeight,
                                MeasureSpec.EXACTLY
                            ),
                            MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST)
                        )
                    }
                }
                TAB_MODE_FLEXIBLE -> {
                    if (totalWidth <= measuredWidth) {
                        // 所有子tab宽度加起来都比parent宽度小，此时跟SPREAD模式一致
                        totalWidth = measuredWidth
                        for (i in 0 until childCount) {
                            val child = getChildAt(i)
                            child.measure(
                                MeasureSpec.makeMeasureSpec(
                                    totalWidth * (child as KDBaseTab).weight / totalWeight,
                                    MeasureSpec.EXACTLY
                                ),
                                MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST)
                            )
                        }
                    } else {
                        // 所有子tab宽度加起来比parent宽度大，此时跟SCROllABLE模式一致
                        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
                    }
                }
            }
        }

        setMeasuredDimension(View.resolveSizeAndState(totalWidth, widthMeasureSpec, 0),
            heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed) {
            var width = 0
            if (tabMode == TAB_MODE_PACK) {
                width = (r - l - totalWidth) / 2
            }
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child.layout(width, 0, width + child.measuredWidth, child.measuredHeight)
                width += child.measuredWidth
            }
            scrollable = r - l < width
            indicator?.init()
        }
    }

    override fun onScrolling(scrollFraction: Float, startItem: Int, endItem: Int) {
        val startTab: View? = getChildAt(startItem)
        val endTab: View? = getChildAt(endItem)

        if (startTab != null && endTab != null) {

            // 整个TabLayout同步滚动
            syncLayoutScrollX(startTab, endTab, scrollFraction)

            // 将当前滚动参数同步给关联到的两个tab
            syncTabScrollState(startTab, endTab, scrollFraction)
        }

        // 将滚动状态同步给indicator
        syncIndicatorScrollState(startItem, endItem, scrollFraction)
    }

    override fun onScrollStateChanged(state: Int) {
        when (state) {
            SCROLL_STATE_IDLE -> updateTabState()
            SCROLL_STATE_DRAGGING -> tabChangeAnimator?.cancel()
        }

        scrollState = state
    }

    private fun updateTabState() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (i == currentItem) {
                (child as KDBaseTab).selectTab()
            } else {
                (child as KDBaseTab).reset()
            }
            child.invalidate()
        }
    }

    private fun syncTabScrollState(startTab: View, endTab: View, fraction: Float) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            when (child) {
                startTab -> (child as KDBaseTab).onScrolling(1 - fraction, startTab.left > endTab.left)
                endTab -> (child as KDBaseTab).onScrolling(fraction, startTab.left < endTab.left)
                else -> (child as KDBaseTab).reset()
            }
            child.invalidate()
        }

        if (fraction == 1f) {
            currentItem = indexOfChild(endTab)
        }
    }

    private fun syncIndicatorScrollState(startItem: Int, endItem: Int, fraction: Float) {
        indicator?.onTabScrolled(startItem, endItem, fraction)
        invalidate()
    }

    /**
     * 根据滚动的两个tab和滚动进程，将tabLayout同步滚动
     */
    private fun syncLayoutScrollX(currentTab: View, nextTab: View, fraction: Float) {
        if (scrollable) {
            val startScrollX = getTabScrollXInCenter(currentTab)
            val endScrollX = getTabScrollXInCenter(nextTab)
            scrollTo(startScrollX + ((endScrollX - startScrollX) * fraction).toInt())
        }
    }

    /**
     * tab在正中间时，整个tabLayout的scrollX值
     */
    private fun getTabScrollXInCenter(tab: View): Int {
        return tab.left - (width - tab.width) / 2 + scrollBiasX.toInt()
    }
}