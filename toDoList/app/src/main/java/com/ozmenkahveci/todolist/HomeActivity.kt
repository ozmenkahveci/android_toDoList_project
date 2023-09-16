package com.ozmenkahveci.todolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.ozmenkahveci.todolist.databinding.ActivityHomeBinding
import java.util.Locale

@Suppress("UNREACHABLE_CODE")
class HomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "To Do List"
        setSupportActionBar(binding.toolbar)

        val locale =
            getLocaleSharedPreferances()?.let { Locale(it) }
        if (locale != null) {
            Locale.setDefault(locale)
        }
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)

        viewPager = binding.sayfaGecisVP
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(ListFragment())
        adapter.addFragment(ShareFragment())
        adapter.addFragment(SurpriseFragment())
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNav.menu.getItem(position).isChecked = true
            }
        })

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.listFragment -> viewPager.currentItem = 0
                R.id.shareFragment -> viewPager.currentItem = 1
                R.id.surpriseFragment -> viewPager.currentItem = 2
                else -> {}
            }
            true
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
    }
        return true
    }

     fun getLocaleSharedPreferances(): String? {
        val sharedPref: SharedPreferences = baseContext.getSharedPreferences("LanguagePref", Context.MODE_PRIVATE)
        return sharedPref.getString("selectedLocale", "")
    }
}