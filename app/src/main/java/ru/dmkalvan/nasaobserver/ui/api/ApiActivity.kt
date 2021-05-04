package ru.dmkalvan.nasaobserver.ui.api

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_api.*
import ru.dmkalvan.nasaobserver.R


private const val YESTERDAY = 0
private const val TODAY = 1
private const val RANDOM = 2

class ApiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        setHighlightedTab(TODAY)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                setHighlightedTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun setHighlightedTab(position: Int) {
val layoutInflater = LayoutInflater.from(this@ApiActivity)

        tab_layout.getTabAt(TODAY)?.customView = null
        tab_layout.getTabAt(YESTERDAY)?.customView = null
        tab_layout.getTabAt(RANDOM)?.customView = null

        when(position){
            TODAY -> {
                setTodayTabHighlighted(layoutInflater)
            }
            YESTERDAY -> {
                setYesterdayTabHighlighted(layoutInflater)
            }
            RANDOM -> {
                setRandomTabHighlighted(layoutInflater)
            }
            else -> {
                setTodayTabHighlighted(layoutInflater)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun setRandomTabHighlighted(layoutInflater: LayoutInflater) {
val random = layoutInflater.inflate(R.layout.activity_api_custom_tab_random, null)
        random.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        tab_layout.getTabAt(YESTERDAY)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_yesterday, null)
        tab_layout.getTabAt(TODAY)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_today, null)
        tab_layout.getTabAt(RANDOM)?.customView = random
    }

    @SuppressLint("InflateParams")
    private fun setYesterdayTabHighlighted(layoutInflater: LayoutInflater) {
        val yesterday = layoutInflater.inflate(R.layout.activity_api_custom_tab_yesterday, null)
        yesterday.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        tab_layout.getTabAt(YESTERDAY)?.customView = yesterday
        tab_layout.getTabAt(TODAY)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_today, null)
        tab_layout.getTabAt(RANDOM)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_random, null)
    }

    @SuppressLint("InflateParams")
    private fun setTodayTabHighlighted(layoutInflater: LayoutInflater) {
        val today = layoutInflater.inflate(R.layout.activity_api_custom_tab_today, null)
        today.findViewById<AppCompatTextView>(R.id.tab_image_textview)
            .setTextColor(
                ContextCompat.getColor(
                    this@ApiActivity,
                    R.color.colorAccent
                )
            )
        tab_layout.getTabAt(YESTERDAY)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_yesterday, null)
        tab_layout.getTabAt(TODAY)?.customView = today
        tab_layout.getTabAt(RANDOM)?.customView = layoutInflater.inflate(R.layout.activity_api_custom_tab_random, null)
    }

}
