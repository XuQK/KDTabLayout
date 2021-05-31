package github.xuqk.kdtablayout.widget

import android.graphics.Canvas
import github.xuqk.kdtablayout.KDTabLayout

/**
 * Created By：XuQK
 * Created Date：2/17/20 1:58 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
abstract class KDTabIndicator(protected val tabLayout: KDTabLayout) {

    /**
     * 初始化方法，在TabLayout布局尺寸改变，或adapter重新设置后会调用
     */
    abstract fun init()

    /**
     * 此方法参数都是以滚动方向为参照
     */
    abstract fun onTabScrolled(startItem: Int, endItem: Int, scrolledFraction: Float)

    /**
     * 绘制Indicator
     */
    abstract fun draw(canvas: Canvas)

    /**
     * 获取Indicator占用的宽度
     * 此方法在且仅在Indicator单独使用时需要实现
     */
    open fun getWidth(): Int = 0

    /**
     * 获取Indicator占用的高度
     * 此方法在且仅在Indicator单独使用时需要实现
     */
    open fun getHeight(): Int = 0

    fun postInvalidate() {
        tabLayout.postInvalidate()
    }
}