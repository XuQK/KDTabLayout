package github.xuqk.kdtablayout.sample

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import github.xuqk.kdtablayout.KDTabAdapter
import github.xuqk.kdtablayout.sample.adapter.ViewPager2Adapter
import github.xuqk.kdtablayout.sample.widget.CustomFontTab
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.indicator.KDRecIndicator
import github.xuqk.kdtablayout.widget.tab.KDColorClipTextTab
import github.xuqk.kdtablayout.widget.tab.KDColorMorphingTextTab
import kotlinx.android.synthetic.main.activity_scrollable_tab.*

/**
 * Created By：XuQK
 * Created Date：2/23/20 4:48 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class ScrollableTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrollable_tab)

        tab0.scrollBiasX = -100f
        tab0.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@ScrollableTabActivity, ZH[position]).apply {
                    horizontalPadding = 16f
                    selectedTextSize = 22f
                    normalTextSize = 16f
                    setOnClickListener {
                        vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return null
            }

            override fun getTabCount(): Int {
                return ZH.size
            }
        }

        tab1.scrollBiasX = -50f
        tab1.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorClipTextTab(this@ScrollableTabActivity, ZH[position]).apply {
                    horizontalPadding = 16f
                    selectedTextSize = 22f
                    normalTextSize = 16f
                    setOnClickListener {
                        vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return null
            }

            override fun getTabCount(): Int {
                return ZH.size
            }
        }

        tab2.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@ScrollableTabActivity, ZH[position]).apply {
                    horizontalPadding = 16f
                    selectedTextColor = Color.parseColor("#ff5722")
                    normalTextColor = Color.parseColor("#9e9e9e")
                    selectedTextSize = 32f
                    normalTextSize = 16f
                    resizeWithFontSize = true
                    setOnClickListener {
                        vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab2).apply {
                    indicatorHeight = 6f
                    color = 0xffff5722.toInt()
                    cornerRadius = 3f
                    mode = KDRecIndicator.MODE_EXACT
                    indicatorWidth = 16f
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return ZH.size
            }
        }

        tab3.scrollBiasX = 50f
        tab3.needCompleteScroll = true
        tab3.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@ScrollableTabActivity, ZH[position]).apply {
                    horizontalPadding = 16f
                    selectedTextColor = Color.parseColor("#ff5722")
                    normalTextColor = Color.BLACK
                    setOnClickListener {
                        vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab3).apply {
                    indicatorHeight = 36f
                    color = 0x4c03a9f4
                    marginBottom = 6f
                    cornerRadius = 18f
                    mode = KDRecIndicator.MODE_MATCH
                }
            }

            override fun getTabCount(): Int {
                return ZH.size
            }
        }

        tab4.scrollBiasX = 100f
        tab4.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@ScrollableTabActivity, ZH[position]).apply {
                    horizontalPadding = 16f
                    selectedTextColor = Color.parseColor("#039be5")
                    normalTextColor = Color.parseColor("#9e9e9e")
                    setOnClickListener {
                        vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab4).apply {
                    indicatorHeight = 6f
                    color = 0xff039be5.toInt()
                    marginBottom = 42f
                    mode = KDRecIndicator.MODE_MATCH
                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return ZH.size
            }
        }

        tab5.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return CustomFontTab(this@ScrollableTabActivity, EN[position]).apply {
                    horizontalPadding = 16f
                    selectedTextColor = Color.parseColor("#673ab7")
                    normalTextColor = Color.parseColor("#9ccc65")
                    setOnClickListener {
                        vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab5).apply {
                    indicatorHeight = 6f
                    color = 0xff673ab7.toInt()
                    mode = KDRecIndicator.MODE_WRAP_CONTENT
                }
            }

            override fun getTabCount(): Int {
                return EN.size
            }
        }

        vp2.adapter = ViewPager2Adapter(ZH.toMutableList())
        tab0.setViewPager2(vp2)
        tab1.setViewPager2(vp2)
        tab2.setViewPager2(vp2)
        tab3.setViewPager2(vp2)
        tab4.setViewPager2(vp2)
        tab5.setViewPager2(vp2)
    }
}