package github.xuqk.kdtablayout.widget.indicator

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import github.xuqk.kdtablayout.HsvEvaluator
import github.xuqk.kdtablayout.KDTabLayout
import github.xuqk.kdtablayout.dpToPx
import github.xuqk.kdtablayout.widget.KDTabIndicator

/**
 * Created By：XuQK
 * Created Date：2/23/20 7:31 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：为了便于计算，让左右各有1/2 space的margin
 */
class DotMorphingIndicator(tabLayout: KDTabLayout) : KDTabIndicator(tabLayout) {

    var normalSize: Float = dpToPx(tabLayout.context, 8f).toFloat()
        set(value) {
            field = dpToPx(tabLayout.context, value).toFloat()
        }
    var selectedSize: Float = dpToPx(tabLayout.context, 12f).toFloat()
        set(value) {
            field = dpToPx(tabLayout.context, value).toFloat()
        }
    var space: Float = dpToPx(tabLayout.context, 8f).toFloat()
        set(value) {
            field = dpToPx(tabLayout.context, value).toFloat()
        }
    var selectedColor: Int = Color.GRAY
    var normalColor: Int = Color.LTGRAY

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dotY: Float = 0f
    private val dotXArray = mutableListOf<Float>()
    private var dotCount: Int = 0
    private val hsvEvaluator = HsvEvaluator()

    private var fraction: Float = 0f
    private var startItem: Int = 0
    private var endItem: Int = 0
    private var startDotSize: Float = 0f
    private var endDotSize: Float = 0f

    /**
     * 初始化方法，在TabLayout布局尺寸改变，或adapter重新设置后会调用
     */
    override fun init() {
        dotXArray.clear()
        dotCount = tabLayout.contentAdapter!!.getTabCount()
        dotXArray.add((space + normalSize) / 2)
        var x: Float = dotXArray[0]
        for (i in 1 until dotCount - 1) {
            x += space + normalSize
            dotXArray.add(x)
        }
        dotXArray.add(getWidth() - (space + normalSize) / 2)

        dotY = tabLayout.height / 2f

        onTabScrolled(tabLayout.currentItem, tabLayout.currentItem, 0f)

        postInvalidate()
    }

    override fun onTabScrolled(startItem: Int, endItem: Int, scrolledFraction: Float) {
        fraction = scrolledFraction
        this.startItem = startItem
        this.endItem = endItem
        startDotSize = selectedSize - (selectedSize - normalSize) * scrolledFraction
        endDotSize = normalSize + (selectedSize - normalSize) * scrolledFraction
    }

    override fun draw(canvas: Canvas) {
        dotXArray.forEachIndexed { index, x ->
            when (index) {
                startItem -> {
                    paint.color = hsvEvaluator.evaluate(fraction, selectedColor, normalColor)
                    canvas.drawCircle(x, dotY, startDotSize / 2, paint)
                }
                endItem -> {
                    paint.color = hsvEvaluator.evaluate(fraction, normalColor, selectedColor)
                    canvas.drawCircle(x, dotY, endDotSize / 2, paint)
                }
                else -> {
                    paint.color = normalColor
                    canvas.drawCircle(x, dotY, normalSize / 2, paint)
                }
            }
        }
    }

    override fun getWidth(): Int {
        return (normalSize * dotCount + space * dotCount).toInt()
    }

    override fun getHeight(): Int {
        return selectedSize.toInt()
    }
}