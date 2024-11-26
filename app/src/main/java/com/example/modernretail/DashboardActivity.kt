package com.example.modernretail

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.modernretail.databinding.ActivityDashboardBinding
import com.example.modernretail.others.AppUtils
import com.example.modernretail.store.StoreAddFrag
import com.google.android.material.navigation.NavigationView
import com.vmadalin.easypermissions.EasyPermissions
import java.io.File
import java.io.FileOutputStream

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    var binding: ActivityDashboardBinding? = null
    val dashView get() = binding!!

    private lateinit var toggle: ActionBarDrawerToggle
    private var backpressed: Long = 0

    public lateinit var toolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_dashboard)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashView.root)
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_blue_steel_dark))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView)
                windowInsetsController!!.isAppearanceLightStatusBars = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewProcess()
    }

    private fun viewProcess() {
        toggle = ActionBarDrawerToggle(
            this,
            dashView.drawer,
            findViewById(R.id.dashToolbar),
            R.string.openDrawer,
            R.string.closeDrawer
        )
        dashView.drawer.addDrawerListener(toggle)
        toggle.syncState()
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.color_white)
        dashView.navView.setNavigationItemSelectedListener(this)

        toolbarTitle = findViewById(R.id.toolbarText)
        dashView.dashToolbar.toolbarHome.setOnClickListener(this)
        dashView.dashToolbar.toolbarNotification.setOnClickListener(this)

        loadFrag(HomeFrag(), HomeFrag::class.java.name)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            dashView.navView.menu.findItem(R.id.menu_home).itemId -> {
                loadFrag(HomeFrag(), HomeFrag::class.java.name)
            }
        }
        dashView.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun loadFrag(mFrag: Fragment, tagValue: String, isAdd:Boolean = false) {
        println("load_frag $tagValue")
        var tag = tagValue.split(".").last().toString()
        if (mFrag is HomeFrag) {
            supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            dashView.dashToolbar.toolbarHome.visibility = View.GONE
        } else {
            dashView.dashToolbar.toolbarHome.visibility = View.VISIBLE
        }
        if (supportFragmentManager.findFragmentByTag(tag) == null || mFrag is HomeFrag) {
            if(isAdd){
                supportFragmentManager.beginTransaction()
                    .add(dashView.fragContainerView.id, mFrag, tag)
                    .setReorderingAllowed(true)
                    .addToBackStack(tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commitAllowingStateLoss()
            }else{
                supportFragmentManager.beginTransaction()
                    .replace(dashView.fragContainerView.id, mFrag, tag)
                    .setReorderingAllowed(true)
                    .addToBackStack(tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commitAllowingStateLoss()
            }
        } else {
            supportFragmentManager.popBackStackImmediate(tag, 0)
            /*supportFragmentManager.beginTransaction()
                .show(supportFragmentManager.findFragmentByTag(tag)!!)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commitAllowingStateLoss()*/
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }

    fun captureImage(){
        var permissionL : ArrayList<String> = ArrayList()
        permissionL.add(android.Manifest.permission.CAMERA)
        if(hasPermission(permissionL.toTypedArray())){
            try {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePictureIntent, AppUtils.ACTION_CAMERA)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }else{
            requestPermission(permissionL.toTypedArray(),AppUtils.REQ_PERMISSION_CAMERA,"Allow Camera Permissions.")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == AppUtils.ACTION_CAMERA){
                val imageBitmap = data?.extras?.get("data") as Bitmap

                val fileDir = this.filesDir
                val fileName = AppUtils.getCurrentDateTime().replace("-","_").replace(" ","_").replace(":","_")
                val photoFile = File(fileDir, "$fileName.png")
                FileOutputStream(photoFile).use { out ->
                    imageBitmap.compress(Bitmap.CompressFormat.PNG,100,out)
                }

                val fm = supportFragmentManager.findFragmentById(dashView.fragContainerView.id)
                if (fm is StoreAddFrag){
                    (fm.updateImage(photoFile))
                }
            }
        }
    }

    private fun hasPermission(permissionList:Array<String>)= EasyPermissions.hasPermissions(this, *permissionList)

    private fun requestPermission(permissionList:Array<String>,reqCode:Int,msg:String){
        EasyPermissions.requestPermissions(this,msg, reqCode,*permissionList)
    }

}