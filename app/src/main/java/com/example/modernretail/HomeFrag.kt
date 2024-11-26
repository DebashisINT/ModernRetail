package com.example.modernretail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.modernretail.databinding.FragHomeBinding
import com.example.modernretail.others.DialogLoading
import com.example.modernretail.store.StoreAddFrag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFrag : Fragment(), View.OnClickListener {
    private var binding: FragHomeBinding? = null
    private val homeView get() = binding!!

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragHomeBinding.inflate(inflater, container, false)
        return homeView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        proceed()
    }

    private fun proceed() {
        homeView.flStores.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            homeView.flStores.id -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    DialogLoading.show(requireActivity().supportFragmentManager, "")
                    withContext(Dispatchers.Main) {
                        (mContext as DashboardActivity).loadFrag(StoreAddFrag(), StoreAddFrag::class.java.name)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (mContext as DashboardActivity).toolbarTitle.text = "Dashboard"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}