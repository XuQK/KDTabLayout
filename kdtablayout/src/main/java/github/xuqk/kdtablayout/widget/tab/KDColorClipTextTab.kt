package github.xuqk.kdtablayout.widget.tab

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import github.xuqk.kdtablayout.dpToPx

/**
 * Created By：XuQK
 * Created Date：2/20/20 11:30 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
@SuppressLint("ViewConstructor")
class KDColorClipTextTab(context: Context, text: String) :
    KDSizeMorphingTextTab(context, text) {

    private var selectedFraction: Float = 0f
    private var selectedInLeft: Boolean = false

    override fun onScrolling(selectedFraction: Float, selectedInLeft: Boolean) {
        super.onScrolling(selectedFraction, selectedInLeft)
        this.selectedFraction = selectedFraction
        this.selectedInLeft = selectedInLeft
    }

    override fun selectTab() {
        super.selectTab()
        selectedFraction = 1f
    }

    override fun reset() {
        super.reset()
        selectedFraction = 0f
    }

    override fun drawContent(canvas: Canvas) {
        paint.isFakeBoldText = true
        paint.textSize = dpToPx(context, textSize).toFloat()
        paint.typeface = if (bold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        paint.textAlign = Paint.Align.CENTER
        paint.getFontMetrics(fontMetrics)
        val baseLine = (fontMetrics.descent - fontMetrics.ascent) / 2 + height / 2 - fontMetrics.descent

        when (selectedFraction) {
            0f -> {
                paint.color = normalTextColor
                canvas.drawText(text, (width / 2).toFloat(), baseLine, paint)
            }
            1f -> {
                paint.color = selectedTextColor
                canvas.drawText(text, (width / 2).toFloat(), baseLine, paint)
            }
            else -> {
                if (selectedInLeft) {
                    val leftWidth = (width * selectedFraction).toInt()

                    // 绘制左半部分
                    drawClipText(canvas, text, selectedTextColor, baseLine,
                        0, 0, leftWidth, height)

                    // 绘制右半部分
                    drawClipText(canvas, text, normalTextColor, baseLine,
                        leftWidth, 0, width, height)
                } else {
                    val leftWidth = (width * (1 - selectedFraction)).toInt()

                    // 绘制左半部分
                    drawClipText(canvas, text, normalTextColor, baseLine,
                        0, 0, leftWidth, height)

                    // 绘制右半部分
                    drawClipText(canvas, text, selectedTextColor, baseLine,
                        leftWidth, 0, width, height)
                }
            }
        }

        computeContentBounds()
    }

    private fun drawClipText(canvas: Canvas, text: String, color: Int, baseLine: Float, left: Int, top: Int, right: Int, bottom: Int) {
        paint.color = color
        canvas.save()
        canvas.clipRect(left, top, right, bottom)
        canvas.drawText(text, (width / 2).toFloat(), baseLine, paint)
        canvas.restore()
    }
}