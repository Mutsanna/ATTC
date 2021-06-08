package com.mutsanna.attc

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mutsanna.attc.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        bottomNav()
    }

    fun tampilkanFragment(nmFragment: Fragment?) {
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
            val fragment : Fragment?
            when (item.itemId) {
                R.id.nav_home -> {
                    tampilkanFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_location -> {
                    tampilkanFragment(LocationFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_history -> {
                    tampilkanFragment(HistoryFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_abou -> {
                    tampilkanFragment(AboutFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }
    }
}