package com.ozmenkahveci.todolist

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozmenkahveci.todolist.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private lateinit var vt:VeritabaniYardimcisi
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vt = VeritabaniYardimcisi(this)

        val db = Listdao()
        val recordExists = db.isRecordExists(vt)
        if (recordExists) {
            startActivity(Intent(this@MainActivity,HomeActivity::class.java))
            finish()

        } else {
            iconGetir()
            titleGetir()
            subTitleGetir()
            buttonGetir()

            binding.baslaBTN.setOnClickListener{
                startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                finish()
            }

        }



    }

    @SuppressLint("Recycle")
    fun iconGetir(){
        val icon = ObjectAnimator.ofFloat(binding.imageView,"translationY",800.0f,0.0f).apply {
            duration = 1000

        }
        icon.start()
    }

    fun titleGetir(){
        val title = ObjectAnimator.ofFloat(binding.titleTV,"translationX",800.0f,0.0f).apply {
            duration = 2000

        }
        title.start()
    }

    fun subTitleGetir(){
        val subTitle = ObjectAnimator.ofFloat(binding.subTitleTV,"translationX",-800.0f,0.0f).apply {
            duration = 2000

        }
        subTitle.start()
    }
    fun buttonGetir(){
        val button = ObjectAnimator.ofFloat(binding.baslaBTN,"translationY",800.0f,0.0f).apply {
            duration = 2000

        }
        button.start()

    }
}