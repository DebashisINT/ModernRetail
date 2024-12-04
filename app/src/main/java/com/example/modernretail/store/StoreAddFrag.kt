package com.example.modernretail.store

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.modernretail.DashboardActivity
import com.example.modernretail.database.StoreEntity
import com.example.modernretail.databinding.FragStoreAddBinding
import com.example.modernretail.others.AppUtils
import com.example.modernretail.others.DateTimeUtils
import com.example.modernretail.others.DialogLoading
import com.example.xst.AppDatabase
import com.example.xst.utils.DialogOk
import com.example.xst.utils.DialogYesNo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class StoreAddFrag : Fragment(), View.OnClickListener {
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
        proceed()
    }

    private fun proceed() {
        storeAddView.ivCaptureImage.setOnClickListener(this)
        storeAddView.btnAddStore.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            storeAddView.ivCaptureImage.id -> {
                (mContext as DashboardActivity).captureImage()
            }

            storeAddView.btnAddStore.id -> {
                DialogYesNo("Want to save the Store?", object : DialogYesNo.OnClick {
                    override fun onYesClick() {
                        saveStore()
                    }

                    override fun onNoClick() {

                    }
                }).show(requireActivity().supportFragmentManager, "")
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

    private fun saveStore() {
        DialogLoading.show(requireActivity().supportFragmentManager, "")
        if (storeAddView.etStoreName.text!!.trim().toString().equals("")) {
            DialogLoading.dismiss()
            storeAddView.etStoreName.setError("Enter Store Name")
            storeAddView.etStoreName.requestFocus()
        }else if(storeAddView.etStoreAddress.text!!.trim().toString().equals("")){
            DialogLoading.dismiss()
            storeAddView.etStoreAddress.setError("Enter Store Address")
            storeAddView.etStoreAddress.requestFocus()
        }else if(storeAddView.etStoreContactName.text!!.trim().toString().equals("")){
            DialogLoading.dismiss()
            storeAddView.etStoreContactName.setError("Enter Contact Name")
            storeAddView.etStoreContactName.requestFocus()
        }else if(storeAddView.etStoreContactNumber.text!!.trim().toString().equals("")){
            DialogLoading.dismiss()
            storeAddView.etStoreContactNumber.setError("Enter Contact Number")
            storeAddView.etStoreContactNumber.requestFocus()
        }else if(storeAddView.etStoreSizeArea.text!!.trim().toString().equals("")){
            DialogLoading.dismiss()
            storeAddView.etStoreSizeArea.setError("Enter Size/Area")
            storeAddView.etStoreSizeArea.requestFocus()
        }else if(storeAddView.etStoreContactEmail.text!!.trim().toString().length >0 && !AppUtils.isValidEmail(storeAddView.etStoreContactEmail.text!!.trim().toString())) {
            DialogLoading.dismiss()
            storeAddView.etStoreContactEmail.setError("Enter valid Email")
            storeAddView.etStoreContactEmail.requestFocus()
        }else{
            lifecycleScope.launch(Dispatchers.IO) {
                var storeObj : StoreEntity = StoreEntity()
                storeObj.store_id = DateTimeUtils.getCurrentDateTime().replace("-","_").replace(" ","_").replace(":","_")
                storeObj.store_name = storeAddView.etStoreName.text!!.trim().toString()
                storeObj.store_address = storeAddView.etStoreAddress.text!!.trim().toString()
                storeObj.store_contact_name = storeAddView.etStoreContactName.text!!.trim().toString()
                storeObj.store_contact_number = storeAddView.etStoreContactNumber.text!!.trim().toString()
                storeObj.store_contact_whatsapp = storeAddView.etStoreContactWhatsapp.text!!.trim().toString()
                storeObj.store_contact_email = storeAddView.etStoreContactEmail.text!!.trim().toString()
                storeObj.store_type = "1"
                storeObj.store_size_area = storeAddView.etStoreSizeArea.text!!.trim().toString()

                AppDatabase.getDatabase(mContext).storeDao.insert(storeObj)

                withContext(Dispatchers.Main){
                    DialogLoading.dismiss()
                    DialogOk("Store saved successfully.",object : DialogOk.OnClick{
                        override fun onOkClick() {
                            (mContext as DashboardActivity).onBackPressed()
                        }
                    }).show(requireActivity().supportFragmentManager, "")
                }
            }
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