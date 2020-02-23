package github.xuqk.kdtablayout.widget

import android.content.Context
import android.graphics.*
import android.view.View
import github.xuqk.kdtablayout.widget.KDTabBadge

/**
 * Created By：XuQK
 * Created Date：2/21/20 1:18 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
abstract class KDBaseTab(
    context: Context) : View(context) {

    protected val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 内容区域在父布局中的位置
     */
    val contentRect = Rect()

    // ------供用户自定义的属性 START
    var badge: KDTabBadge? = null
    var weight: Int = 1
    /**单位dp*/
    var horizontalPadding: Float = 0f
    // ------供用户自定义的属性 END

    abstract fun onScrolling(selectedFraction: Float, selectedInLeft: Boolean)

    abstract fun reset()

    abstract fun selectTab()

    /**
     * 计算内容尺寸
     */
    abstract fun computeContentBounds()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawContent(canvas)
        drawBadge(canvas)
    }

    protected open fun drawContent(canvas: Canvas) {
    }

    protected fun drawBadge(canvas: Canvas) {
        badge?.draw(canvas)
    }
}