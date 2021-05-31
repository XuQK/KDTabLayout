package github.xuqk.kdtablayout.widget.badge

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.annotation.Px
import github.xuqk.kdtablayout.dpToPx
import github.xuqk.kdtablayout.widget.KDBadge
import github.xuqk.kdtablayout.widget.KDTab

/**
 * Created By：XuQK
 * Created Date：2/16/20 2:53 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
abstract class KDNumberBadge(tab: KDTab) : KDBadge(tab) {
    protected val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    protected val fontMetrics = Paint.FontMetrics()

    protected var animator: ValueAnimator? = null
    protected var fraction: Float = 1f

    /**指示数字*/
    var count: Int = 0
        set(value) {
            field = value
            if (showCount) {
                tab.invalidate()
            }
        }
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
     * badge的尺寸
     */
    @get:Px
    var size: Float = 0f
        set(value) {
            field = dpToPx(tab.context, value).toFloat()
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

    override fun show() {
        show = true
        tab.invalidate()
    }
}