package com.example.modernretail.store

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modernretail.DashboardActivity
import com.example.modernretail.R
import com.example.modernretail.api.SyncApi
import com.example.modernretail.database.StoreDtls
import com.example.modernretail.database.StoreEntity
import com.example.modernretail.databinding.FragStoreAddBinding
import com.example.modernretail.databinding.FragStoreBinding
import com.example.modernretail.others.AppUtils
import com.example.modernretail.others.BottomSheetMsg
import com.example.modernretail.others.DialogLoading
import com.example.modernretail.others.Pref
import com.example.xst.AppDatabase
import com.example.xst.utils.DialogOk
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar

class StoreFrag: Fragment(), View.OnClickListener {
    private var binding: FragStoreBinding? = null
    private val storeView get() = binding!!

    private lateinit var storeViewModel: StoreViewModel
    private lateinit var storeAdapter: StoreAdapter
    //private var isDragging: Boolean = false
    //private var initialX = 0f
    //private var initialY = 0f

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragStoreBinding.inflate(inflater, container, false)
        return storeView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                DialogLoading.show(requireActivity().supportFragmentManager, "")
                proceed()
            } finally {
                DialogLoading.dismiss()
            }
        }
    }

    private fun proceed() {
        storeViewModel = ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)
        storeAdapter = StoreAdapter(mContext, object : StoreAdapter.OnClick {
            override fun onCallCLick(obj: StoreDtls) {
                try {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${obj.store_contact_number}")
                    }
                    if (intent.resolveActivity(mContext.packageManager) != null) {
                        mContext.startActivity(intent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onSyncCLick(obj: StoreDtls) {
                DialogLoading.show(requireActivity().supportFragmentManager, "")
                lifecycleScope.launch(Dispatchers.IO) {
                    if(AppUtils.isOnline(mContext)){
                        val response = SyncApi.syncStoreApi(mContext,obj.store_id)
                        if (response.status.equals("200")){
                            AppDatabase.getDatabase(mContext).storeDao.updateIsUploaded(true,obj.store_id)
                        }
                        withContext(Dispatchers.Main){
                            DialogLoading.dismiss()
                            DialogOk("Hi ${Pref.UserName}","Store synced successfully.",object : DialogOk.OnClick{
                                override fun onOkClick() {

                                }
                            }).show(requireActivity().supportFragmentManager, "")
                        }
                    }else{
                        DialogLoading.dismiss()
                        BottomSheetMsg(ContextCompat.getString(mContext,R.string.msg_no_internet)).show(requireActivity().supportFragmentManager, "msg")
                    }
                }
            }
        })
        storeView.rvStoreL.adapter = storeAdapter

        storeViewModel.storeSearch.observe(viewLifecycleOwner) { pagingData ->
            storeAdapter.submitData(lifecycle, pagingData)
        }

        storeView.searchStore.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false    //true if the query has been handled by the listener, false to let the SearchView perform the default action.
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                storeViewModel.setSearch(newText ?: "")
                if (newText.isNullOrEmpty()) {
                    AppUtils.hideKeyboardClearFocus(mContext as Activity)
                    storeView.rvStoreL.smoothScrollToPosition(0)
                    //storeView.rvStoreL.scrollToPosition(0)
                }
                return false
            }
        })

        storeView.fabAddStore.setOnClickListener {
            (mContext as DashboardActivity).loadFrag(StoreAddFrag(), StoreAddFrag::class.java.name)
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch (Dispatchers.IO){
            var storeL = AppDatabase.getDatabase(mContext).storeDao.getAll() as ArrayList<StoreEntity>
            withContext(Dispatchers.Main){
                (mContext as DashboardActivity).toolbarTitle.text = if(storeL.size>0) "Store(s) : ${storeL.size}" else "Store(s)"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        storeView.searchStore.setQuery("", false)
        storeView.searchStore.clearFocus()
        binding = null
    }
}