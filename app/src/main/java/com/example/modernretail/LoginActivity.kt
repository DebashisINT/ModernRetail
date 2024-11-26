package com.example.modernretail

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.example.modernretail.databinding.ActivityLoginBinding
import com.example.modernretail.others.AppUtils

class LoginActivity : AppCompatActivity(),View.OnClickListener {

    private var binding: ActivityLoginBinding? = null
    private val loginView get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginView.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_white))

        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.isAppearanceLightStatusBars = true

        viewProcess()
    }

    private fun viewProcess(){
        loginView.tvAppVersion.text = "App Version : "+AppUtils.getAppVersion(this)
        loginView.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            loginView.btnLogin.id ->{
                doLogin()
                if (!loginView.etUsername.text!!.trim().toString().equals("")) {
                    if (!loginView.etPassword.text!!.trim().toString().equals("")) {
                        //doLogin()
                    } else {
                        loginView.etPassword.setError("Please enter Password")
                        loginView.etPassword.requestFocus()
                    }
                } else {
                    loginView.etUsername.setError("Please enter Username")
                    loginView.etUsername.requestFocus()
                }
            }
        }
    }

    private fun doLogin(){
        AppUtils.hideKeyboardClearFocus(this)
        val anim = ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle()
        startActivity(Intent(this, DashboardActivity::class.java),anim)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}