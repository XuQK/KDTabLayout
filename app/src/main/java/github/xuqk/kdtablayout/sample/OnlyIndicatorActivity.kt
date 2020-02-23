package github.xuqk.kdtablayout.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.xuqk.kdtablayout.KDTabAdapter
import github.xuqk.kdtablayout.KDTabLayout
import github.xuqk.kdtablayout.sample.adapter.ViewPager2Adapter
import github.xuqk.kdtablayout.sample.widget.DotIndicator
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.indicator.KDRecIndicator
import kotlinx.android.synthetic.main.activity_only_indicator.*

/**
 * Created By：XuQK
 * Created Date：2/23/20 6:51 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class OnlyIndicatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_only_indicator)

        val data = ZH

        tab0.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return null
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
//                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        tab1.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return null
            }

            override fun createIndicator(): KDTabIndicator? {
                return DotIndicator(tab1).apply {
                    strokeWidth = 2f
//                    color = 0xffff5722.toInt()
//                    marginBottom = 42f
//                    marginHorizontal = 14f
//                    paddingHorizontal = 14f
//                    cornerRadius = 18f
//                    mode = KDRecIndicator.MODE_MATCH
//                    indicatorWidth = 16f
//                    startInterpolator = AccelerateInterpolator()
//                    endInterpolator = DecelerateInterpolator(2f)
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        tab0.setViewPager2(vp2)
        tab1.setViewPager2(vp2)
        tab2.setViewPager2(vp2)
        vp2.adapter = ViewPager2Adapter(data.toMutableList())
    }
}