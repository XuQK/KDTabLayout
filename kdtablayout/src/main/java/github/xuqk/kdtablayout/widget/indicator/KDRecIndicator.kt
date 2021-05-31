package github.xuqk.kdtablayout.widget.indicator

import android.graphics.*
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.Px
import github.xuqk.kdtablayout.KDTabLayout
import github.xuqk.kdtablayout.dpToPx
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator

/**
 * Created By：XuQK
 * Created Date：2/17/20 1:41 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：矩形Indicator
 */
open class KDRecIndicator(tabLayout: KDTabLayout) : KDTabIndicator(tabLayout) {

    companion object {
        /**
         * Indicator宽度匹配Tab两端，此时marginHorizontal参数会生效
         */
        const val MODE_MATCH = 0
        /**
         * Indicator宽度为固定值
         */
        const val MODE_EXACT = 1
        /**
         * Indicator宽度匹配内容区域宽度，此时paddingHorizontal参数会生效
         * 如果只有Indicator，没有Tab，那么此参数不生效
         */
        const val MODE_WRAP_CONTENT = 2
    }

    protected val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    protected val rect = RectF()
    protected val context = tabLayout.context

    // ----------供用户自定义的属性 START
    /**
     * Indicator高度
     * 输入值单位为dp，输出值为px
     */
    @get:Px
    var indicatorHeight: Float = 0f
        set(value) {
            field = dpToPx(context, value).toFloat()
        }
    /**
     * Indicator离底部的距离
     * 输入值单位为dp，输出值为px
     */
    @get:Px
    var marginBottom: Float = 0f
        set(value) {
            field = dpToPx(context, value).toFloat()
        }
    /**
     * Indicator横向Margin，仅在mode为MODE_MATCH时或单独使用Indicator时有效
     * 输入值单位为dp，输出值为px
     */
    @get:Px
    var marginHorizontal: Float = 0f
        set(value) {
            field = dpToPx(context, value).toFloat()
        }
    /**
     * Indicator横向Padding，仅在mode为MODE_WRAP_CONTENT时有效
     * 输入值单位为dp，输出值为px
     */
    @get:Px
    var paddingHorizontal: Float = 0f
        set(value) {
            field = dpToPx(context, value).toFloat()
        }
    /**
     * Indicator圆角数值
     * 输入值单位为dp，输出值为px
     */
    @get:Px
    var cornerRadius: Float = 0f
        set(value) {
            field = dpToPx(context, value).toFloat()
        }
    /**
     * Indicator宽度，仅在mode为MODE_EXACT时或单独使用Indicator时有效
     * 输入值单位为dp，输出值为px
     */
    @get:Px
    var indicatorWidth: Float = 0f
        set(value) {
            field = dpToPx(context, value).toFloat()
        }
    /**
     * MODE_MATCH Indicator宽度匹配整个TAB的宽度，此时indicatorWidth参数无效
     * MODE_EXACT Indicator宽度为固定值，此时marginHorizontal参数无效
     */
    var mode: Int = MODE_MATCH
    /**
     * Indicator颜色，为ColorInt值
     */
    @field:ColorInt
    var color: Int = Color.BLACK
        set(value) {
            field = value
            startColor = field
            endColor = field
        }

    /**
     * 渐变色start
     */
    @field:ColorInt
    var startColor: Int = Color.BLACK

    /**
     * 渐变色end
     */
    @field:ColorInt
    var endColor: Int = Color.BLACK

    var startInterpolator: Interpolator = LinearInterpolator()
    var endInterpolator: Interpolator = LinearInterpolator()
    // ----------供用户自定义的属性 END

    override fun init() {
        onTabScrolled(tabLayout.currentItem, tabLayout.currentItem, 0f)
        postInvalidate()
    }

    override fun onTabScrolled(startItem: Int, endItem: Int, scrolledFraction: Float) {
        val fractionToRight: Float = if (endItem > startItem) {
            // 滚向右边
            scrolledFraction
        } else {
            // 滚向左边
            1 - scrolledFraction
        }

        val leftX: Int
        val rightX: Int
        val nextLeftX: Int
        val nextRightX: Int
        if (tabLayout.childCount == 0) {
            val tabWidth = tabLayout.width / tabLayout.contentAdapter!!.getTabCount()
            val leftItem: Int
            val rightItem: Int
            if (endItem > startItem) {
                leftItem = startItem
                rightItem = endItem
            } else {
                leftItem = endItem
                rightItem = startItem
            }
            when (mode) {
                MODE_EXACT -> {
                    leftX = (leftItem * tabWidth + (tabWidth - indicatorWidth) / 2).toInt()
                    rightX = ((leftItem + 1) * tabWidth - (tabWidth - indicatorWidth) / 2).toInt()
                    nextLeftX = (rightItem * tabWidth + (tabWidth - indicatorWidth) / 2).toInt()
                    nextRightX = ((rightItem + 1) * tabWidth - (tabWidth - indicatorWidth) / 2).toInt()
                }
                MODE_MATCH -> {
                    leftX = leftItem * tabWidth + marginHorizontal.toInt()
                    rightX = (leftItem + 1) * tabWidth - marginHorizontal.toInt()
                    nextLeftX = rightItem * tabWidth + marginHorizontal.toInt()
                    nextRightX = (rightItem + 1) * tabWidth - marginHorizontal.toInt()
                }
                else -> {
                    throw IllegalArgumentException("此时mode必须为MODE_EXACT或MODE_MATCH之一")
                }
            }
        } else {
            val leftTab: KDTab
            val rightTab: KDTab
            if (endItem > startItem) {
                // 滚向右边
                leftTab = tabLayout.getChildAt(startItem) as KDTab
                rightTab = tabLayout.getChildAt(endItem) as KDTab
            } else {
                // 滚向左边
                leftTab = tabLayout.getChildAt(endItem) as KDTab
                rightTab = tabLayout.getChildAt(startItem) as KDTab
            }

            when (mode) {
                MODE_EXACT -> {
                    leftX = (leftTab.left + (leftTab.width - indicatorWidth) / 2).toInt()
                    rightX = (leftTab.right - (leftTab.width - indicatorWidth) / 2).toInt()
                    nextLeftX = (rightTab.left + (rightTab.width - indicatorWidth) / 2).toInt()
                    nextRightX = (rightTab.right - (rightTab.width - indicatorWidth) / 2).toInt()
                }
                MODE_WRAP_CONTENT -> {
                    leftTab.computeContentBounds()
                    rightTab.computeContentBounds()
                    leftX = leftTab.contentRect.left - paddingHorizontal.toInt()
                    rightX = leftTab.contentRect.right + paddingHorizontal.toInt()
                    nextLeftX = rightTab.contentRect.left - paddingHorizontal.toInt()
                    nextRightX = rightTab.contentRect.right + paddingHorizontal.toInt()
                }
                MODE_MATCH -> {
                    leftX = leftTab.left + marginHorizontal.toInt()
                    rightX = leftTab.right - marginHorizontal.toInt()
                    nextLeftX = rightTab.left + marginHorizontal.toInt()
                    nextRightX = rightTab.right - marginHorizontal.toInt()
                }
                // MODE_MATCH
                else -> {
                    throw IllegalArgumentException("mode必须为MODE_EXACT或MODE_MATCH之一")
                }
            }
        }
        rect.top = tabLayout.height - indicatorHeight - marginBottom
        rect.bottom = tabLayout.height - marginBottom
        rect.left =
            leftX + (nextLeftX - leftX) * startInterpolator.getInterpolation(fractionToRight)
        rect.right =
            rightX + (nextRightX - rightX) * endInterpolator.getInterpolation(fractionToRight)
    }

    override fun draw(canvas: Canvas) {
        val y = (rect.bottom - rect.top) / 2
        paint.shader = LinearGradient(
            rect.left,
            y,
            rect.right,
            y,
            startColor,
            endColor,
            Shader.TileMode.CLAMP
        )
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
    }

    override fun getWidth(): Int {
        return ((indicatorWidth + marginHorizontal * 2) * tabLayout.contentAdapter!!.getTabCount()).toInt()
    }

    override fun getHeight(): Int {
        return indicatorHeight.toInt()
    }
}