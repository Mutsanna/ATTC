package com.mutsanna.attc.camera.slideshow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.mutsanna.attc.R
import com.mutsanna.attc.camera.CameraActivity
import com.mutsanna.attc.databinding.ActivityHomeBinding
import com.mutsanna.attc.databinding.ActivitySlideBinding


class SlideActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slideViewPager: ViewPager2 = findViewById(R.id.viewPager2)
        val dotLayout: LinearLayout = findViewById(R.id.layoutDots)
    }

}