package github.xuqk.kdtablayout.widget.badge

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.Px
import github.xuqk.kdtablayout.dpToPx
import github.xuqk.kdtablayout.getBaselineToCenter
import github.xuqk.kdtablayout.widget.KDTab

/**
 * Created By：XuQK
 * Created Date：2/21/20 9:20 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：tab的圆形指示badge，定位中心点为Tab内容区域的right和top
 */
class KDContentEndRelativeBadge(tab: KDTab) : KDNumberBadge(tab) {
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