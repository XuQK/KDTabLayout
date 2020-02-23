package github.xuqk.kdtablayout.widget

import android.graphics.Canvas

/**
 * Created By：XuQK
 * Created Date：2/16/20 2:53 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
interface KDTabBadge {
    fun dismiss()

    fun show()

    fun draw(canvas: Canvas)
}