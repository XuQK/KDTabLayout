package github.xuqk.kdtablayout.sample

import android.graphics.Color
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import github.xuqk.kdtablayout.KDTabAdapter
import github.xuqk.kdtablayout.KDTabLayout
import github.xuqk.kdtablayout.sample.adapter.ViewPager2Adapter
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.badge.KDContentEndRelativeBadge
import github.xuqk.kdtablayout.widget.badge.KDTabEndRelativeBadge
import github.xuqk.kdtablayout.widget.indicator.KDRecIndicator
import github.xuqk.kdtablayout.widget.tab.KDColorMorphingTextTab
import kotlinx.android.synthetic.main.activity_badge_tab.*

/**
 * Created By：XuQK
 * Created Date：2/23/20 6:08 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class BadgeTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badge_tab)

        val data = ZH.copyOfRange(0, 3)

        tab0.tabMode = KDTabLayout.TAB_MODE_SPREAD
        tab0.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@BadgeTabActivity, data[position]).apply {
                    weight = position + 2
                    selectedTextColor = Color.parseColor("#ff5722")
                    normalTextColor = Color.parseColor("#9e9e9e")
                    selectedTextSize = 18f
                    normalTextSize = 14f
                    selectedBold = true
                    setOnClickListener {
                        vp2.currentItem = position
                    }

                    badge = KDTabEndRelativeBadge(this).apply {
                        count = position
                        showCount = true
                        size = 12f
                        marginEnd = 12f
                        marginTop = 12f
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab0).apply {
                    indicatorHeight = 4f
                    color = 0xffff5722.toInt()
                    mode = KDRecIndicator.MODE_MATCH
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
                return KDColorMorphingTextTab(this@BadgeTabActivity, data[position]).apply {
                    horizontalPadding = 12f
                    selectedTextColor = Color.parseColor("#039be5")
                    normalTextColor = Color.parseColor("#9e9e9e")
                    setOnClickListener {
                        vp2.currentItem = position
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
                return KDRecIndicator(tab1).apply {
                    indicatorHeight = 4f
                    color = 0xff039be5.toInt()
                    mode = KDRecIndicator.MODE_MATCH
                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        tab0.setViewPager2(vp2)
        tab1.setViewPager2(vp2)
        vp2.adapter = ViewPager2Adapter(data.toMutableList())
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tab0.getTab(position)?.badge?.dismiss()
            }
        })
    }
}