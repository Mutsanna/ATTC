package com.mutsanna.attc.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mutsanna.attc.camera.CameraActivity
import com.mutsanna.attc.R
import com.mutsanna.attc.camera.slideshow.SlideActivity
import com.mutsanna.attc.databinding.ActivityHomeBinding
import com.mutsanna.attc.location.LocationFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(1).isEnabled = false

        binding.btnCamera.setOnClickListener {
            val moveIntent = Intent(this@HomeActivity, SlideActivity::class.java)
            startActivity(moveIntent)
        }

        bottomNav()
    }

    private fun tampilkanFragment(nmFragment: Fragment?) {
        if (nmFragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_content, nmFragment, nmFragment.javaClass.simpleName)
                .commit()
        }
    }

    private fun bottomNav() {
        val tabMenu = binding.bottomNavigationView
        tabMenu.itemIconTintList = null
        tampilkanFragment(HomeFragment())
        tabMenu.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    tampilkanFragment(HomeFragment())
                    binding.txtJudul.text = resources.getString(R.string.judul_home)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_location -> {
                    tampilkanFragment(LocationFragment())
                    binding.txtJudul.text = resources.getString(R.string.judul_lokasi)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }
    }
}