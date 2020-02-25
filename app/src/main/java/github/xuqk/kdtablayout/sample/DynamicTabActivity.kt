package github.xuqk.kdtablayout.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.xuqk.kdtablayout.KDTabAdapter
import github.xuqk.kdtablayout.sample.adapter.ViewPager2Adapter
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.indicator.KDRecIndicator
import github.xuqk.kdtablayout.widget.tab.KDColorClipTextTab
import kotlinx.android.synthetic.main.activity_dynamic_tab.*
import java.util.*

/**
 * Created By：XuQK
 * Created Date：2/23/20 5:23 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class DynamicTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_tab)

        val data = mutableListOf<String>()
        data.addAll(ZH)

        vp2.adapter = ViewPager2Adapter(data)

        tab.setViewPager2(vp2)
        tab.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorClipTextTab(this@DynamicTabActivity, data[position]).apply {
                    horizontalPadding = 16f
                    selectedTextSize = 22f
                    normalTextSize = 16f
                    setOnClickListener {
                        vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(tab).apply {
                    indicatorHeight = 4f
                    color = 0x4cff0000
                    marginBottom = 4f
                    cornerRadius = 2f
                    mode = KDRecIndicator.MODE_MATCH
                }
            }

            override fun getTabCount(): Int {
                return data.size
            }
        }

        btn.setOnClickListener {
            var n = Random().nextInt(ZH.size + 1)
            if (n == 0) n = ZH.size
            val newData = ZH.copyOfRange(0, n)
            data.clear()
            data.addAll(newData)

            vp2.adapter!!.notifyDataSetChanged()
            tab.init()
        }
    }
}