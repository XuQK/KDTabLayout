package github.xuqk.kdtablayout.widget.badge

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.annotation.Px
import github.xuqk.kdtablayout.dpToPx
import github.xuqk.kdtablayout.getBaselineToCenter
import github.xuqk.kdtablayout.widget.KDTabBadge
import github.xuqk.kdtablayout.widget.KDBaseTab

/**
 * Created By：XuQK
 * Created Date：2/21/20 9:20 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：tab的圆形指示badge，定位中心点为Tab内容区域的right和top
 */
class KDContentEndRelativeBadge(private val tab: KDBaseTab) : KDTabBadge {
    private var show: Boolean = true
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fontMetrics = Paint.FontMetrics()

    private var animator: ValueAnimator? = null
    private var fraction: Float = 1f

    /**指示数字*/
    var count: Int = 0
    /**是否展示数字*/
    var showCount: Boolean = false
    /**badge背景颜色*/
    @field:ColorInt
    var bgColor: Int = Color.RED
    /**数字颜色*/
    @field:ColorInt
    var countTextColor: Int = Color.WHITE
    /**数字字体大小*/
    var countTextSize: Float = dpToPx(tab.context, 8f).toFloat()
        set(value) {
            field = dpToPx(tab.context, value).toFloat()
        }

    /**
     * badge中心点离tab right的距离
     */
    @get:Px
    var xBias: Float = 0f
        set(value) {
            field = dpToPx(tab.context, value).toFloat()
        }
    /**
     * badge中心点离tab top的距离
     */
    @get:Px
    var yBias: Float = 0f
        set(value) {
            field = dpToPx(tab.context, value).toFloat()
        }
    /**
     * badge的尺寸
     */
    @get:Px
    var size: Float = 0f
        set(value) {
            field = dpToPx(tab.context, value).toFloat()
        }

    override fun show() {
        show = true
        tab.invalidate()
    }

    override fun dismiss() {
        if (!show) return
        animator?.cancel()
        animator = ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 200
            addUpdateListener {
                fraction = it.animatedValue as Float
                if (fraction == 0f) {
                    show = false
                }
                tab.invalidate()
            }
            start()
        }
    }

    override fun draw(canvas: Canvas) {
        if (!show) return

        tab.computeContentBounds()
        val x = tab.contentRect.right - tab.left
        val y = tab.contentRect.top

        // 绘制背景
        paint.color = bgColor
        canvas.drawCircle(x + xBias, y + yBias, size / 2 * fraction, paint)

        if (showCount) {
            // 绘制数字
            paint.color = countTextColor
            paint.textSize = countTextSize * fraction
            paint.textAlign = Paint.Align.CENTER
            paint.getFontMetrics(fontMetrics)

            val baseLineY = fontMetrics.getBaselineToCenter() + y + yBias
            canvas.drawText(count.toString(), x + xBias, baseLineY, paint)
        }
    }
}