package github.xuqk.kdtablayout.widget.indicator

import android.graphics.Canvas
import android.graphics.Paint
import github.xuqk.kdtablayout.KDTabLayout
import github.xuqk.kdtablayout.dpToPx
import github.xuqk.kdtablayout.widget.KDTabIndicator

/**
 * Created By：XuQK
 * Created Date：2/23/20 7:31 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：为了便于计算，让左右各有1/2 space的margin
 */
class DotWithStrokeIndicator(tabLayout: KDTabLayout) : KDTabIndicator(tabLayout) {

    var size: Float = dpToPx(tabLayout.context, 8f).toFloat()
        set(value) {
            field = dpToPx(tabLayout.context, value).toFloat()
        }
    var space: Float = dpToPx(tabLayout.context, 8f).toFloat()
        set(value) {
            field = dpToPx(tabLayout.context, value).toFloat()
        }
    var color: Int = 0xff2196f3.toInt()
    var strokeWidth: Float = 0f
        set(value) {
            field = dpToPx(tabLayout.context, value).toFloat()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dotY: Float = 0f
    private val dotXArray = mutableListOf<Float>()
    private var currentDotX: Float = 0f
    private var dotCount: Int = 0

    /**
     * 初始化方法，在TabLayout布局尺寸改变，或adapter重新设置后会调用
     */
    override fun init() {
        dotXArray.clear()
        dotCount = tabLayout.contentAdapter!!.getTabCount()
        dotXArray.add((space + size) / 2)
        var x: Float = dotXArray[0]
        for (i in 1 until dotCount - 1) {
            x += space + size
            dotXArray.add(x)
        }
        dotXArray.add(getWidth() - (space + size) / 2)

        dotY = tabLayout.height / 2f

        onTabScrolled(tabLayout.currentItem, tabLayout.currentItem, 0f)

        postInvalidate()
    }

    override fun onTabScrolled(startItem: Int, endItem: Int, scrolledFraction: Float) {
        var leftItem: Int = startItem
        var rightItem: Int = endItem
        var fractionToRight: Float = scrolledFraction
        if (endItem < startItem) {
            // 滚向右边
            leftItem = endItem
            rightItem = startItem
            fractionToRight = 1 - scrolledFraction
        }

        currentDotX = (dotXArray[rightItem] - dotXArray[leftItem]) * fractionToRight + dotXArray[leftItem]
    }

    override fun draw(canvas: Canvas) {
        paint.reset()
        paint.isAntiAlias = true
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        dotXArray.forEach {
            canvas.drawCircle(it, dotY, size / 2, paint)
        }

        paint.reset()
        paint.isAntiAlias = true
        paint.color = color
        canvas.drawCircle(
            currentDotX,
            dotY,
            size / 2,
            paint
        )
    }

    override fun getWidth(): Int {
        return (size * dotCount + space * dotCount).toInt()
    }

    override fun getHeight(): Int {
        return (size + strokeWidth).toInt()
    }
}