package github.xuqk.kdtablayout.sample.adapter

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.xuqk.kdtablayout.sample.ZH

/**
 * Created By：XuQK
 * Created Date：2/23/20 5:25 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class ViewPager2Adapter(private val data: MutableList<String> = mutableListOf<String>()) : RecyclerView.Adapter<ViewPager2Adapter.ViewPager2Holder>() {

    fun setNewData(newData: List<String>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2Holder {
        val tv = TextView(parent.context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            setTextColor(Color.RED)
            gravity = Gravity.CENTER
        }
        tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewPager2Holder(
            tv
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewPager2Holder, position: Int) {
        (holder.itemView as TextView).text = data[position] + "\n\nViewPager2"
    }

    class ViewPager2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}