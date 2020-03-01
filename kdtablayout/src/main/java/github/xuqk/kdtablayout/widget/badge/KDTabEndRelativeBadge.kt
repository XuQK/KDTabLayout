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
 * Description：tab的圆形指示badge，定位中心点为Tab的right和top
 */
class KDTabEndRelativeBadge(tab: KDTab) : KDNumberBadge(tab) {
    /**
     * badge中心点离tab right的距离
     */
    @get:Px
    var marginEnd: Float = 0f
        set(value) {
            field = dpToPx(tab.context, value).toFloat()
        }
    /**
     * badge中心点离tab top的距离
     */
    @get:Px
    var marginTop: Float = 0f
        set(value) {
            field = dpToPx(tab.context, value).toFloat()
        }

    override fun draw(canvas: Canvas) {
        if (!show) return

        // 绘制背景
        paint.color = bgColor
        canvas.drawCircle(tab.width - marginEnd, marginTop, size / 2 * fraction, paint)

        if (showCount) {
            // 绘制数字
            paint.color = countTextColor
            paint.textSize = countTextSize * fraction
            paint.textAlign = Paint.Align.CENTER
            paint.getFontMetrics(fontMetrics)

            val baseLineY = fontMetrics.getBaselineToCenter() + marginTop
            canvas.drawText(count.toString(), tab.width - marginEnd, baseLineY, paint)
        }
    }
}