package github.xuqk.kdtablayout.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_scrollable_tab.setOnClickListener {
            startActivity(
                Intent(this, ScrollableTabActivity::class.java)
            )
        }

        btn_fixed_tab.setOnClickListener {
            startActivity(
                Intent(this, FixedTabActivity::class.java)
            )
        }

        btn_dynamic_tab.setOnClickListener {
            startActivity(
                Intent(this, DynamicTabActivity::class.java)
            )
        }

        btn_only_indicator_tab.setOnClickListener {
            startActivity(
                Intent(this, OnlyIndicatorActivity::class.java)
            )
        }

        btn_badge_tab.setOnClickListener {
            startActivity(
                Intent(this, BadgeTabActivity::class.java)
            )
        }

        btn_custom_tab.setOnClickListener {
            startActivity(
                Intent(this, CustomTabActivity::class.java)
            )
        }
    }
}

