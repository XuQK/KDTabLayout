package github.xuqk.kdtablayout.sample

import android.graphics.Color
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import github.xuqk.kdtablayout.KDTabAdapter
import github.xuqk.kdtablayout.KDTabLayout
import github.xuqk.kdtablayout.sample.adapter.ViewPagerAdapter
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.indicator.KDRecIndicator
import github.xuqk.kdtablayout.widget.tab.KDColorClipTextTab
import github.xuqk.kdtablayout.widget.tab.KDColorMorphingTextTab
import kotlinx.android.synthetic.main.activity_fixed_tab.*

/**
 * Created By：XuQK
 * Created Date：2/23/20 4:57 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class FixedTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fixed_tab)

        val data = ZH.copyOfRange(0, 3)

        tab0.tabMode = KDTabLayout.TAB_MODE_SPREAD
        tab0.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@FixedTabActivity, data[position]).apply {
//                    horizontalPadding = 16f
                    weight = position + 2
                    selectedTextColor = Color.parseColor("#ff5722")
                    normalTextColor = Color.parseColor("#9e9e9e")
                    selectedTextSize = 18f
                    normalTextSize = 14f
                    selectedBold = true
                    setOnClickListener {
                        vp.currentItem = position
                    }

//                    badge = KDContentEndRelativeBadge(this).apply {
//                        count = position
//                        showCount = true
//                        size = 12f
//                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab0).apply {
                    indicatorHeight = 4f
                    color = 0xffff5722.toInt()
//                    marginBottom = 42f
//                    marginHorizontal = 14f
//                    paddingHorizontal = 14f
//                    cornerRadius = 18f
                    mode = KDRecIndicator.MODE_MATCH
//                    indicatorWidth = 16f
//                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        tab1.tabMode = KDTabLayout.TAB_MODE_PACK
        tab1.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@FixedTabActivity, data[position]).apply {
                    horizontalPadding = 12f
                    selectedTextColor = Color.parseColor("#039be5")
                    normalTextColor = Color.parseColor("#9e9e9e")
//                    selectedTextSize = 18f
//                    normalTextSize = 14f
//                    selectedBold = true
                    setOnClickListener {
                        vp.currentItem = position
                    }

//                    badge = KDContentEndRelativeBadge(this).apply {
//                        count = position
//                        showCount = true
//                        size = 12f
//                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab1).apply {
                    indicatorHeight = 4f
                    color = 0xff039be5.toInt()
//                    marginBottom = 42f
//                    marginHorizontal = 14f
//                    paddingHorizontal = 14f
//                    cornerRadius = 18f
                    mode = KDRecIndicator.MODE_MATCH
//                    indicatorWidth = 16f
//                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        tab2.tabMode = KDTabLayout.TAB_MODE_PACK
        tab2.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorClipTextTab(this@FixedTabActivity, data[position]).apply {
                    horizontalPadding = 12f
                    selectedTextColor = Color.parseColor("#ffffff")
                    normalTextColor = Color.parseColor("#ff5722")
//                    selectedTextSize = 18f
//                    normalTextSize = 14f
//                    selectedBold = true
                    setOnClickListener {
                        vp.currentItem = position
                    }

//                    badge = KDContentEndRelativeBadge(this).apply {
//                        count = position
//                        showCount = true
//                        size = 12f
//                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab2).apply {
                    indicatorHeight = 48f
                    color = 0xffff5722.toInt()
//                    marginBottom = 42f
//                    marginHorizontal = 14f
//                    paddingHorizontal = 14f
                    cornerRadius = 24f
                    mode = KDRecIndicator.MODE_MATCH
//                    indicatorWidth = 16f
//                    startInterpolator = AccelerateInterpolator()
//                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        tab0.setViewPager(vp)
        tab1.setViewPager(vp)
        tab2.setViewPager(vp)
        vp.adapter = ViewPagerAdapter(data.toMutableList())
    }
}