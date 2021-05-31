package github.xuqk.kdtablayout.sample

import android.graphics.Color
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import github.xuqk.kdtablayout.KDTabAdapter
import github.xuqk.kdtablayout.KDTabLayout
import github.xuqk.kdtablayout.sample.adapter.ViewPagerAdapter
import github.xuqk.kdtablayout.sample.databinding.ActivityFixedTabBinding
import github.xuqk.kdtablayout.sample.widget.TextViewTab
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.indicator.KDRecIndicator
import github.xuqk.kdtablayout.widget.tab.KDColorClipTextTab
import github.xuqk.kdtablayout.widget.tab.KDColorMorphingTextTab

/**
 * Created By：XuQK
 * Created Date：2/23/20 4:57 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class FixedTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFixedTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFixedTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = ZH.copyOfRange(0, 3)

        binding.tab0.tabMode = KDTabLayout.TAB_MODE_SPREAD
        binding.tab0.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@FixedTabActivity, data[position]).apply {
                    weight = position + 2
                    selectedTextColor = Color.parseColor("#ff5722")
                    normalTextColor = Color.parseColor("#9e9e9e")
                    selectedTextSize = 18f
                    normalTextSize = 14f
                    selectedBold = true
                    setOnClickListener {
                        binding.vp.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(binding.tab0).apply {
                    indicatorHeight = 4f
                    startColor = 0xffFF4343.toInt()
                    endColor = 0x00FF7444.toInt()
                    mode = KDRecIndicator.MODE_MATCH
                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        binding.tab1.tabMode = KDTabLayout.TAB_MODE_PACK
        binding.tab1.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorMorphingTextTab(this@FixedTabActivity, data[position]).apply {
                    horizontalPadding = 12f
                    selectedTextColor = Color.parseColor("#039be5")
                    normalTextColor = Color.parseColor("#9e9e9e")
                    setOnClickListener {
                        binding.vp.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(binding.tab1).apply {
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

        binding.tab2.tabMode = KDTabLayout.TAB_MODE_PACK
        binding.tab2.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorClipTextTab(this@FixedTabActivity, data[position]).apply {
                    horizontalPadding = 12f
                    selectedTextColor = Color.parseColor("#ffffff")
                    normalTextColor = Color.parseColor("#ff5722")
                    setOnClickListener {
                        binding.vp.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(binding.tab2).apply {
                    indicatorHeight = 48f
                    color = 0xffff5722.toInt()
                    cornerRadius = 24f
                    mode = KDRecIndicator.MODE_MATCH
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        binding.tab3.tabMode = KDTabLayout.TAB_MODE_SPREAD
        binding.tab3.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return TextViewTab(this@FixedTabActivity, data[position]).apply {
                    setOnClickListener {
                        binding.vp.currentItem = position
                    }
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        binding.tab0.setViewPager(binding.vp)
        binding.tab1.setViewPager(binding.vp)
        binding.tab2.setViewPager(binding.vp)
        binding.tab3.setViewPager(binding.vp)
        binding.vp.adapter = ViewPagerAdapter(data.toMutableList())
    }
}