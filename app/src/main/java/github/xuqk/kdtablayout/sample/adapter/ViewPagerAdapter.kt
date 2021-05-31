package github.xuqk.kdtablayout.sample.adapter

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

/**
 * Created By：XuQK
 * Created Date：2/23/20 5:28 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class ViewPagerAdapter(private val data: MutableList<String> = mutableListOf<String>()) : PagerAdapter() {

    fun setNewData(newData: Set<String>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val tv = TextView(container.context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            setTextColor(Color.RED)
            text = data[position] + "\n\nViewPager"
            gravity = Gravity.CENTER
        }
        tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        container.addView(tv)
        return tv
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}