package com.example.modernretail

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.modernretail.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null
    private val splashView get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_splash)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashView.root)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_green_splash))

        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.isAppearanceLightStatusBars = false

        val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_shake)
        splashView.tvAppName.startAnimation(shakeAnimation)

        Handler(Looper.getMainLooper()).postDelayed(kotlinx.coroutines.Runnable {
            splashView.tvAppName.clearAnimation()
            val anim = ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle()
            startActivity(Intent(this, LoginActivity::class.java),anim)
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}