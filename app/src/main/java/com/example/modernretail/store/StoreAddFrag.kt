package com.example.modernretail.store

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.modernretail.DashboardActivity
import com.example.modernretail.databinding.FragStoreAddBinding
import com.example.modernretail.others.DialogLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class StoreAddFrag: Fragment(), View.OnClickListener {
    private var binding: FragStoreAddBinding? = null
    private val storeAddView get() = binding!!

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragStoreAddBinding.inflate(inflater, container, false)
        return storeAddView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DialogLoading.dismiss()
        proceed()
    }

    private fun proceed() {
        storeAddView.ivCaptureImage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            storeAddView.ivCaptureImage.id ->{
                (mContext as DashboardActivity).captureImage()
            }
        }
    }

    fun updateImage(file: File) {
        try {
            storeAddView.ivStoreImage.setImageURI(Uri.fromFile(file))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        (mContext as DashboardActivity).toolbarTitle.text = "Add Store"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}