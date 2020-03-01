package github.xuqk.kdtablayout.sample

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.xuqk.kdtablayout.KDTabAdapter
import github.xuqk.kdtablayout.KDTabLayout
import github.xuqk.kdtablayout.sample.adapter.ViewPager2Adapter
import github.xuqk.kdtablayout.sample.widget.ShadowGradientTab
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator
import kotlinx.android.synthetic.main.activity_custom_tab.*

/**
 * Created By：XuQK
 * Created Date：2/29/20 9:44 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class CustomTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_tab)

        val data = ZH.copyOfRange(0, 4)

        tab0.tabMode = KDTabLayout.TAB_MODE_SPREAD
        tab0.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return ShadowGradientTab(this@CustomTabActivity, data[position]).apply {
                    selectedTextColor = Color.WHITE
                    normalTextColor = 0xFFBDC1C9.toInt()
                    selectedTextSize = 16f
                    normalTextSize = 12f
                    setOnClickListener {
                        vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return null
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        tab0.setViewPager2(vp2)
        vp2.adapter = ViewPager2Adapter(data.toMutableList())
    }
}