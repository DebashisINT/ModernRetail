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
import androidx.lifecycle.lifecycleScope
import com.example.modernretail.database.StoreEntity
import com.example.modernretail.databinding.ActivityLoginBinding
import com.example.modernretail.others.AppUtils
import com.example.modernretail.others.DateTimeUtils
import com.example.modernretail.others.DialogLoading
import com.example.xst.AppDatabase
import com.example.xst.utils.DialogOk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
       /* DialogLoading.show(supportFragmentManager, "")
        lifecycleScope.launch(Dispatchers.IO) {
            for(i in 0..1000){
                var storeObj : StoreEntity = StoreEntity()
                storeObj.store_id = DateTimeUtils.getCurrentDateTime().replace("-","_").replace(" ","_").replace(":","_")+"$i"
                storeObj.store_name = "A$i"
                storeObj.store_address = "Kolkata"
                storeObj.store_contact_name = "Sam"
                storeObj.store_contact_number = "9830916971"
                storeObj.store_contact_whatsapp = "9830916971"
                storeObj.store_contact_email = ""
                storeObj.store_type = "1"
                storeObj.store_size_area = ""

                AppDatabase.getDatabase(this@LoginActivity).storeDao.insert(storeObj)
            }
            withContext(Dispatchers.Main){
                DialogLoading.dismiss()
                DialogOk("Store saved successfully.",object : DialogOk.OnClick{
                    override fun onOkClick() {
                        (this@LoginActivity as DashboardActivity).onBackPressed()
                    }
                }).show(supportFragmentManager, "")
            }
        }*/
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