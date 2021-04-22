package github.xuqk.kdtablayout.widget.tab

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import androidx.annotation.ColorInt
import github.xuqk.kdtablayout.dpToPx
import github.xuqk.kdtablayout.getBaselineToCenter
import github.xuqk.kdtablayout.widget.KDTab

/**
 * Created By：XuQK
 * Created Date：2/21/20 3:06 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
@SuppressLint("ViewConstructor")
open class KDSizeMorphingTextTab(context: Context, text: String) :
    KDTab(context) {

    protected val fontMetrics = Paint.FontMetrics()

    // ------供用户自定义的属性 START
    /**单位dp*/
    var selectedTextSize: Float = 16f
    /**单位dp*/
    var normalTextSize: Float = 16f
    // 是否随字体尺寸变化调整tab尺寸
    var resizeWithFontSize: Boolean = false
    var selectedBold: Boolean = false
    @ColorInt
    var selectedTextColor: Int = 0xFFFFFFFF.toInt()
    @ColorInt
    var normalTextColor: Int = 0x4CFFFFFF
    // ------供用户自定义的属性 END

    protected var textSize: Float = normalTextSize
    protected var textColor: Int = selectedTextColor
    protected var bold: Boolean = false

    var text: String = ""
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
                postInvalidate()
            }
        }

    init {
        this.text = text
    }

    /**
     * Tab滚动中
     * @param selectedFraction 该tab被选中的比例[0, 1]
     * @param selectedInLeft 该tab被选中区域是否在左边，否就是在右边
     */
    override fun onScrolling(selectedFraction: Float, selectedInLeft: Boolean) {
        if (selectedBold) {
            bold = selectedFraction > 0.5f
        }
        textSize = (selectedTextSize - normalTextSize) * selectedFraction + normalTextSize
        if (resizeWithFontSize && selectedTextSize != normalTextSize) {
            requestLayout()
        }
    }

    /**
     * 该tab被选中时回调
     */
    override fun selectTab() {
        bold = selectedBold
        textSize = selectedTextSize
        textColor = selectedTextColor
    }

    /**
     * 回复至初始状态
     */
    override fun reset() {
        bold = false
        textSize = normalTextSize
        textColor = normalTextColor
    }

    override fun computeContentBounds() {
        setPaintParam()

        val contentWidth = contentRect.width()
        val contentHeight = contentRect.height()
        contentRect.left = left + (width - contentWidth) / 2
        contentRect.right = contentRect.left + contentWidth
        contentRect.top = (height - contentHeight) / 2
        contentRect.bottom = contentRect.top + contentHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setPaintParam()

        val w = paint.measureText(text) + dpToPx(context, horizontalPadding) * 2
        setMeasuredDimension(
            resolveSizeAndState(w.toInt(), widthMeasureSpec, 0),
            MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST)
        )
    }

    override fun drawContent(canvas: Canvas) {
        setPaintParam()

        paint.getFontMetrics(fontMetrics)
        val baselineY = fontMetrics.getBaselineToCenter() + height / 2
        canvas.drawText(text, (width / 2).toFloat(), baselineY, paint)
    }

    protected open fun setPaintParam() {
        paint.reset()
        paint.isAntiAlias = true
        paint.textSize = dpToPx(context, textSize).toFloat()
        paint.color = textColor
        paint.typeface = if (bold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        paint.textAlign = Paint.Align.CENTER
    }
}