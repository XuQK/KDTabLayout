package github.xuqk.kdtablayout.sample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import github.xuqk.kdtablayout.widget.tab.KDColorMorphingTextTab

/**
 * Created By：XuQK
 * Created Date：21-3-5 下午2:57
 * Creator Email：xu.qiankun@xiji.com
 * Description：
 */

@SuppressLint("ViewConstructor")
class CustomFontTab(context: Context, text: String) : KDColorMorphingTextTab(context, text) {

    private val customTypeface by lazy {
        Typeface.createFromAsset(context.assets, "chilanka.otf")
    }

    override fun setPaintParam() {
        super.setPaintParam()
        paint.typeface = customTypeface
    }
}