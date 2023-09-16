package com.ozmenkahveci.todolist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozmenkahveci.todolist.databinding.ActivitySettingsBinding
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.ayarlarToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val locale =
            getLocaleSharedPreferances()?.let { Locale(it) }// shared preferances'a en son hangi dil değerini yazmışsak, uygulamayı o dilde açıyor, uygulama ilk defa açılıyorsa, default olan Türkçe ile açılıyor.
        if (locale != null) {
            Locale.setDefault(locale)
        }
        val config = Configuration()
        config.locale = locale
        this.resources.updateConfiguration(config,this.resources.displayMetrics)

        binding.turkceBTN.setOnClickListener {
            setLocale(this,"")
            restartApp()
        }
        binding.ingilizceBTN.setOnClickListener {
            setLocale(this,"en")
            restartApp()
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_custom_back)
        }

    }
    fun setLocale(context: Context, selectedLocale:String){

        val locale = Locale(selectedLocale)
        Locale.setDefault(locale)

        val config =Configuration()
        config.locale =locale

        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
        writeLocaleSharedPreferences(context,selectedLocale)


    }
    private fun writeLocaleSharedPreferences(context: Context, selectedLocale: String) {
        val sharedPref = context.getSharedPreferences("LanguagePref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("selectedLocale", selectedLocale)
        editor.apply()
    }
    private fun restartApp() {
        val intent = this.packageManager.getLaunchIntentForPackage(this.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }
    private fun getLocaleSharedPreferances(): String? {
        val sharedPref: SharedPreferences = this.getSharedPreferences("LanguagePref", Context.MODE_PRIVATE)
        return sharedPref.getString("selectedLocale", "")
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Geri dönme tuşuna basıldığında geri dönme işlevini tetikle
        this.finish()
        return true
    }

}
