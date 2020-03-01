package github.xuqk.kdtablayout.sample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import github.xuqk.kdtablayout.sample.dpToPx
import github.xuqk.kdtablayout.widget.tab.KDColorMorphingTextTab

/**
 * Created By：XuQK
 * Created Date：3/1/20 12:37 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
@SuppressLint("ViewConstructor")
class ShadowGradientTab(context: Context, text: String) : KDColorMorphingTextTab(context, text) {
    private val paddingHorizontal = dpToPx(context, 5f)
    private val paddingVertical = dpToPx(context, 10f)
    private val shadowRadius = dpToPx(context, 4f).toFloat()
    private val shadowY = dpToPx(context, 5f).toFloat()
    private val rectRadius = dpToPx(context, 10f)
    private val rect = RectF()

    private val startColorRBG = intArrayOf(38, 160, 244)
    private val endColorRBG = intArrayOf(38, 105, 244)
    private val normalBgColor = Color.WHITE

    private var fraction: Float = 0f

    override fun onScrolling(selectedFraction: Float, selectedInLeft: Boolean) {
        super.onScrolling(selectedFraction, selectedInLeft)
        fraction = selectedFraction
    }

    override fun reset() {
        super.reset()
        fraction = 0f
    }

    override fun selectTab() {
        super.selectTab()
        fraction = 1f
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            rect.set(
                paddingHorizontal.toFloat(),
                paddingVertical.toFloat(),
                (width - paddingHorizontal).toFloat(),
                (height - paddingVertical).toFloat()
            )
        }
    }

    override fun drawContent(canvas: Canvas) {
        // 绘制白色底色
        paint.reset()
        paint.color = normalBgColor
        paint.alpha = 255
        paint.setShadowLayer(shadowRadius * fraction, 0f, shadowY * fraction, 0x4c26A0F4.toInt())
        canvas.drawRoundRect(rect, rectRadius.toFloat(), rectRadius.toFloat(), paint)

        // 如果fraction大于0，绘制覆盖的渐变色
        if (fraction > 0f) {
            paint.reset()
            val start = Color.argb((255 * fraction).toInt(), startColorRBG[0], startColorRBG[1], startColorRBG[2])
            val end = Color.argb((255 * fraction).toInt(), endColorRBG[0], endColorRBG[1], endColorRBG[2])

            paint.shader = LinearGradient(
                rect.left,
                rect.bottom,
                rect.right,
                rect.top,
                start,
                end,
                Shader.TileMode.CLAMP
            )
            canvas.drawRoundRect(rect, rectRadius.toFloat(), rectRadius.toFloat(), paint)
        }

        super.drawContent(canvas)
    }
}