package github.xuqk.kdtablayout

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

/**
 * Created By：XuQK
 * Created Date：2/19/20 3:30 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class KDViewPagerHelper {
    /**
     * ViewPager的currentItem会在滚动状态变为SETTING之后马上改变
     * 因为onPageSelected方法会在滚动状态变为SETTING后马上调用
     */
    private var prevTotalPositionOffset: Float = 0F

    var stateListener: ViewPageStateListener? = null

    fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (prevTotalPositionOffset < position + positionOffset) {

            if (positionOffset == 0f && position > prevTotalPositionOffset.toInt()) {
                stateListener?.onScrolling(1f, position - 1, position)
            } else {
                stateListener?.onScrolling(positionOffset, position, position + 1)
            }

        } else if (prevTotalPositionOffset > position + positionOffset) {
            stateListener?.onScrolling(1 - positionOffset, position + 1, position)
        }

        prevTotalPositionOffset = position + positionOffset
    }

    fun onPageSelected(position: Int) {
        stateListener?.onTabSelected(position)
    }

    fun onPageScrollStateChanged(state: Int) {
        stateListener?.onScrollStateChanged(state)
    }

    fun bindViewPager2(viewPager: ViewPager2) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                this@KDViewPagerHelper.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                this@KDViewPagerHelper.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                this@KDViewPagerHelper.onPageScrollStateChanged(state)
            }
        })
    }

    fun bindViewPager(viewPager: ViewPager) {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                this@KDViewPagerHelper.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                this@KDViewPagerHelper.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                this@KDViewPagerHelper.onPageSelected(position)
            }

        })
    }

    interface ViewPageStateListener {
        /**
         * 从currentItem到nextItem的滚动过程中的fraction变化
         * @param scrollFraction 从一个tab到另一个tab，是0-1
         * @param startItem 从currentItem的tab出发
         * @param endItem 到nextItem的tab
         */
        fun onScrolling(scrollFraction: Float, startItem: Int, endItem: Int)

        /**
         * 滚动状态变化
         */
        fun onScrollStateChanged(state: Int)

        fun onTabSelected(position: Int)
    }
}