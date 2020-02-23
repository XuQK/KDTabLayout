package github.xuqk.kdtablayout.widget

import android.graphics.Canvas

/**
 * Created By：XuQK
 * Created Date：2/17/20 1:58 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
abstract class KDTabIndicator {

    /**
     * 初始化方法，在TabLayout布局尺寸改变，或adapter重新设置后会调用
     */
    abstract fun init()

    abstract fun onTabScrolled(startItem: Int, endItem: Int, fraction: Float)

    abstract fun draw(canvas: Canvas)

    /**
     * 获取Indicator占用的宽度
     * 此方法在且仅在Indicator单独使用时需要实现
     */
    open fun getWidth(): Int = 0
}