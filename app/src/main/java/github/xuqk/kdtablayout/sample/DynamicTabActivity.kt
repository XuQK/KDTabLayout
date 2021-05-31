package github.xuqk.kdtablayout.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.xuqk.kdtablayout.KDTabAdapter
import github.xuqk.kdtablayout.sample.adapter.ViewPager2Adapter
import github.xuqk.kdtablayout.sample.databinding.ActivityDynamicTabBinding
import github.xuqk.kdtablayout.widget.KDTab
import github.xuqk.kdtablayout.widget.KDTabIndicator
import github.xuqk.kdtablayout.widget.indicator.KDRecIndicator
import github.xuqk.kdtablayout.widget.tab.KDColorClipTextTab
import java.util.*

/**
 * Created By：XuQK
 * Created Date：2/23/20 5:23 PM
 * Creator Email：xuqiankun66@gmail.com
 * Description：
 */
class DynamicTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDynamicTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDynamicTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = mutableListOf<String>()
        data.addAll(ZH)

        binding.vp2.adapter = ViewPager2Adapter(data)

        binding.tab.setViewPager2(binding.vp2)
        binding.tab.contentAdapter = object : KDTabAdapter() {
            override fun createTab(position: Int): KDTab? {
                return KDColorClipTextTab(this@DynamicTabActivity, data[position]).apply {
                    horizontalPadding = 16f
                    selectedTextSize = 22f
                    normalTextSize = 16f
                    setOnClickListener {
                        binding.vp2.currentItem = position
                    }
                }
            }

            override fun createIndicator(): KDTabIndicator? {
                return KDRecIndicator(binding.tab).apply {
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

        binding.btn.setOnClickListener {
            var n = Random().nextInt(ZH.size + 1)
            if (n == 0) n = ZH.size
            val newData = ZH.copyOfRange(0, n)
            data.clear()
            data.addAll(newData)

            binding.vp2.adapter!!.notifyDataSetChanged()
            binding.tab.init()
        }
    }
}