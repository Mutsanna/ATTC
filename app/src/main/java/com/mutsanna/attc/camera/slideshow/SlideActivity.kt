package com.mutsanna.attc.camera.slideshow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.mutsanna.attc.R
import com.mutsanna.attc.databinding.ActivityHomeBinding
import com.mutsanna.attc.databinding.ActivitySlideBinding
import com.mutsanna.attc.home.HomeActivity

class SlideActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySlideBinding

    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var dotLayout: TabLayout? = null
    var onBoardingViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dotLayout = findViewById(R.id.layoutDots)

        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData(resources.getString(R.string.slide_salah), resources.getString(R.string.desc1), R.drawable.gbs1))
        onBoardingData.add(OnBoardingData(resources.getString(R.string.slide_salah), resources.getString(R.string.desc2), R.drawable.gbs2))
        onBoardingData.add(OnBoardingData(resources.getString(R.string.slide_benar), resources.getString(R.string.desc3), R.drawable.gbs3))
        onBoardingData.add(OnBoardingData(resources.getString(R.string.slide_benar), resources.getString(R.string.desc4), R.drawable.gbs4))

        setOnBoardingViewPagerAdapter(onBoardingData)

        binding.btnBack.setOnClickListener {
            val moveIntent = Intent(this@SlideActivity, HomeActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>) {
        onBoardingViewPager = findViewById(R.id.view_slide)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        dotLayout?.setupWithViewPager(onBoardingViewPager)
    }
}