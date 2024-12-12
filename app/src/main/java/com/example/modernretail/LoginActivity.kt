package com.example.modernretail

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.example.mineee.api.ApiCall
import com.example.modernretail.databinding.ActivityLoginBinding
import com.example.modernretail.others.AppUtils
import com.example.modernretail.others.BottomSheetMsg
import com.example.modernretail.others.DialogLoading
import com.example.modernretail.others.GpsUtils
import com.example.modernretail.others.LocationUtils
import com.example.modernretail.others.Pref
import com.example.xst.AppDatabase
import com.vmadalin.easypermissions.EasyPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity(), View.OnClickListener,EasyPermissions.PermissionCallbacks {

    private var binding: ActivityLoginBinding? = null
    private val loginView get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginView.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_white))

        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.isAppearanceLightStatusBars = true

        checkGPS()
        viewProcess()
    }

    private fun checkGPS(){
        if(!LocationUtils.isGpsEnabled(this)){
            GpsUtils(this).turnOnGps(this,AppUtils.GPS)
        }else{
            checkPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == AppUtils.GPS){
                checkPermission()
            }
        }
    }

    private fun checkPermission(){
        var permissionL: ArrayList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasPermission(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS)))
                permissionL.add(android.Manifest.permission.POST_NOTIFICATIONS)
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (!hasPermission(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                permissionL.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                permissionL.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        if (!hasPermission(arrayOf(android.Manifest.permission.CAMERA))) {
            permissionL.add(android.Manifest.permission.CAMERA)
        }
        if (!hasPermission(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION))) {
            permissionL.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
            permissionL.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        requestPermission(permissionL.toTypedArray(), 999, "Allow Permissions.")
    }

    private fun hasPermission(permissionList: Array<String>) = EasyPermissions.hasPermissions(this, *permissionList)

    private fun requestPermission(permissionList: Array<String>, reqCode: Int, msg: String) {
        EasyPermissions.requestPermissions(this, msg, reqCode, *permissionList)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (requestCode == 999) {

        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 999) {
            var permissionL: ArrayList<String> = ArrayList()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                viewProcess()
            }
        }
    }

    private fun viewProcess() {
        loginView.tvAppVersion.text = "App Version : " + AppUtils.getAppVersion(this)
        loginView.btnLogin.setOnClickListener(this)

        val colorStateList = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
            intArrayOf(
                ContextCompat.getColor(this, R.color.color_blue_steel),
                ContextCompat.getColor(this, R.color.color_charcoal)
            ) // Checked color, unchecked color
        )
        loginView.cbRememberMe.buttonTintList = colorStateList
        loginView.cbRememberMe.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && !loginView.etUsername.text.toString()
                    .equals("") && !loginView.etPassword.text.toString().equals("")
            ) {
                Pref.IsLoginRemember = true
                Pref.LoginUserID = loginView.etUsername.text.toString()
                Pref.LoginPassword = loginView.etPassword.text.toString()
                loginView.cbRememberMe.buttonTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.color_blue_steel))
            } else {
                Pref.IsLoginRemember = false
                Pref.LoginUserID = ""
                Pref.LoginPassword = ""
                loginView.cbRememberMe.isChecked = false
                loginView.cbRememberMe.buttonTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.color_charcoal))
            }
        }

        if (Pref.IsLoginRemember as Boolean) {
            loginView.etUsername.setText(Pref.LoginUserID as String)
            loginView.etPassword.setText(Pref.LoginPassword as String)
            loginView.cbRememberMe.isChecked = true
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            loginView.btnLogin.id -> {
                if (!loginView.etUsername.text!!.trim().toString().equals("")) {
                    if (!loginView.etPassword.text!!.trim().toString().equals("")) {
                        if(AppUtils.isOnline(this)){
                            doLogin(
                                loginView.etUsername.text!!.trim().toString(),
                                loginView.etPassword.text!!.trim().toString(),
                                AppUtils.getAppVersion(this),
                                ""
                            )
                        }else{
                            BottomSheetMsg(ContextCompat.getString(this,R.string.msg_no_internet)).show(supportFragmentManager, "msg")
                        }
                    } else {
                        loginView.etPassword.setError("Please enter Password")
                        loginView.etPassword.requestFocus()
                    }
                }
                else {
                    loginView.etUsername.setError("Please enter Username")
                    loginView.etUsername.requestFocus()
                }
            }
        }
    }

    private fun doLogin(userName: String, password: String, appV: String, deviceToken: String) {
        AppUtils.hideKeyboardClearFocus(this)
        lifecycleScope.launch(Dispatchers.IO) {
            var response = ApiCall.create().login(userName, password, appV, deviceToken)
            withContext(Dispatchers.Main){
                if (response.status.equals("200")) {
                    Pref.user_id = response.user_id
                    Pref.UserName = response.user_name
                    Pref.UserContactNumber = response.contact_number
                    getData()
                }else{
                    BottomSheetMsg(response.message).show(supportFragmentManager, "msg")
                }
            }
        }
    }

    private fun getData(){
        DialogLoading.show(supportFragmentManager, "")
        val loginDataApiJob = lifecycleScope.launch(Dispatchers.Main) {
            launch(Dispatchers.IO) {//get store type
                try {
                    if (AppDatabase.getDatabase(this@LoginActivity).storeTypeDao.getAll().size == 0) {
                        var response = ApiCall.create().getStoreType(Pref.user_id)
                        if (response.status.equals("200")) {
                            AppDatabase.getDatabase(this@LoginActivity).storeTypeDao.deleteAll()
                            AppDatabase.getDatabase(this@LoginActivity).storeTypeDao.insertAll(response.store_type_list)
                        }
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            launch(Dispatchers.IO) {//get city state
                try {
                    if (AppDatabase.getDatabase(this@LoginActivity).pinStateDao.getAll().size == 0) {
                        var response = ApiCall.create().getPinState(Pref.user_id)
                        if (response.status.equals("200")) {
                            AppDatabase.getDatabase(this@LoginActivity).pinStateDao.deleteAll()
                            AppDatabase.getDatabase(this@LoginActivity).pinStateDao.insertAll(response.pin_state_list)
                        }
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        loginDataApiJob.invokeOnCompletion {
            this.runOnUiThread {
                DialogLoading.dismiss()
                Pref.IsLogdedIn = true
                val anim = ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle()
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java),anim)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}