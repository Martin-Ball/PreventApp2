package com.martin.preventapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.martin.preventapp.R
import com.martin.preventapp.databinding.ActivitySplashScreenBinding
import com.martin.preventapp.Model.login.LoginActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var topAnim:Animation
    private lateinit var binding:ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen)
        binding.ImageViewLogoLinkiar.startAnimation(animation)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1000)
    }
}