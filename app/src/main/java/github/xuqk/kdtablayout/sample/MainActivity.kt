package github.xuqk.kdtablayout.sample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.badge.KDContentEndRelativeBadge
import github.xuqk.kdtablayout.widget.indicator.KDRecIndicator
import github.xuqk.kdtablayout.widget.KDBaseTab
import github.xuqk.kdtablayout.widget.tab.KDColorMorphingTextTab
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn0.setOnClickListener { vp.setCurrentItem(2, false) }
        btn1.setOnClickListener { vp.setCurrentItem(3, false) }
        btn2.setOnClickListener { tab.setCurrentItem(github.xuqk.kdtablayout.s.size - 2) }

//        tab.setOnClickListener { it.scrollBy(300, 0) }

        tab.scrollBiasX = 100f
        tab.tabMode = github.xuqk.kdtablayout.KDTabLayout.TAB_MODE_SCROLLABLE
        tab.contentAdapter = object : github.xuqk.kdtablayout.KDTabAdapter() {
            override fun createTab(position: Int): KDBaseTab? {
//                return null
                return KDColorMorphingTextTab(this@MainActivity,
                    github.xuqk.kdtablayout.s[position]).apply {
                    weight = 1
                    horizontalPadding = 8f
                    selectedTextSize = 20f
                    normalTextSize = 16f
                    setOnClickListener {
                        vp2.setCurrentItem(position)
                        badge?.dismiss()
                    }

                    badge = KDContentEndRelativeBadge(this).apply {
                        count = position
                        showCount = true
                        size = 12f
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab).apply {
                    indicatorHeight = 70f
                    color = 0x4cff0000
                    marginBottom = 5f
                    marginHorizontal = 14f
                    paddingHorizontal = 14f
                    cornerRadius = 5f
                    mode = KDRecIndicator.MODE_EXACT
                    indicatorWidth = 16f
                    startInterpolator = AccelerateInterpolator()
//                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return github.xuqk.kdtablayout.s.size
            }
        }



        vp2.adapter = VpAdapter()
        tab.setViewPager2(vp2)

        vp.adapter = object : PagerAdapter() {
            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return github.xuqk.kdtablayout.s.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val tv = TextView(this@MainActivity).apply {
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
                    setTextColor(Color.RED)
                    text = github.xuqk.kdtablayout.s[position] + "---ViewPager"
                }
                tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                container.addView(tv)


                return tv
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
        tab.setViewPager(vp)
    }
}

class VpAdapter : RecyclerView.Adapter<VpHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpHolder {
        val tv = TextView(parent.context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            setTextColor(Color.RED)
        }
        tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return VpHolder(tv)
    }

    override fun getItemCount(): Int {
        return github.xuqk.kdtablayout.s.size
    }

    override fun onBindViewHolder(holder: VpHolder, position: Int) {
        (holder.itemView as TextView).text = github.xuqk.kdtablayout.s[position] + "---ViewPager2"
    }

}

class VpHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

