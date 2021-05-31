package github.xuqk.kdtablayout.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.xuqk.kdtablayout.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScrollableTab.setOnClickListener {
            startActivity(
                Intent(this, ScrollableTabActivity::class.java)
            )
        }

        binding.btnFixedTab.setOnClickListener {
            startActivity(
                Intent(this, FixedTabActivity::class.java)
            )
        }

        binding.btnDynamicTab.setOnClickListener {
            startActivity(
                Intent(this, DynamicTabActivity::class.java)
            )
        }

        binding.btnOnlyIndicatorTab.setOnClickListener {
            startActivity(
                Intent(this, OnlyIndicatorActivity::class.java)
            )
        }

        binding.btnBadgeTab.setOnClickListener {
            startActivity(
                Intent(this, BadgeTabActivity::class.java)
            )
        }

        binding.btnCustomTab.setOnClickListener {
            startActivity(
                Intent(this, CustomTabActivity::class.java)
            )
        }
    }
}

