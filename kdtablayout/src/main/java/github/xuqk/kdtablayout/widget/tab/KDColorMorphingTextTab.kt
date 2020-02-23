package github.xuqk.kdtablayout.widget.tab

import android.annotation.SuppressLint
import android.content.Context
import github.xuqk.kdtablayout.HsvEvaluator

/**
 * Created By：XuQK
 * Created Date：2/21/20 4:24 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
@SuppressLint("ViewConstructor")
open class KDColorMorphingTextTab(context: Context, text: String) :
    KDSizeMorphingTextTab(context, text) {

    private val hsvEvaluator = HsvEvaluator()

    override fun onScrolling(selectedFraction: Float, selectedInLeft: Boolean) {
        super.onScrolling(selectedFraction, selectedInLeft)
        textColor = hsvEvaluator.evaluate(selectedFraction, normalTextColor, selectedTextColor)
    }
}