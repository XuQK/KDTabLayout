package github.xuqk.kdtablayout.sample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.tab.KDColorMorphingTextTab

/**
 * Created By：XuQK
 * Created Date：21-3-5 下午2:57
 * Creator Email：xu.qiankun@xiji.com
 * Description：
 */

@SuppressLint("ViewConstructor")
class TextViewTab(context: Context, text: String) : KDTab(context) {

    private val tv: TextView = TextView(context).apply {
        this.text = text
        textSize = 16f
    }

    init {
        addView(
            tv,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
        )
    }

    override fun onScrolling(selectedFraction: Float, selectedInLeft: Boolean) {
        if (selectedFraction > 0.5f) {
            tv.typeface = Typeface.DEFAULT_BOLD
        } else {
            tv.typeface = Typeface.DEFAULT
        }
    }

    override fun reset() {
        tv.typeface = Typeface.DEFAULT
    }

    override fun selectTab() {
        tv.typeface = Typeface.DEFAULT_BOLD
    }

    override fun computeContentBounds() {

    }
}