package com.example.modernretail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.modernretail.api.SyncApi
import com.example.modernretail.compFeed.ComplaintFeedback
import com.example.modernretail.databinding.FragHomeBinding
import com.example.modernretail.store.StoreFrag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

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
        homeView.flComplaintFeedback.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            homeView.flStores.id -> {
                (mContext as DashboardActivity).loadFrag(StoreFrag(), StoreFrag::class.java.name)
            }
            homeView.flComplaintFeedback.id -> {
                //(mContext as DashboardActivity).loadFrag(ComplaintFeedback(), ComplaintFeedback::class.java.name)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (mContext as DashboardActivity).toolbarTitle.text = "Dashboard"
        (mContext as DashboardActivity).dashView.dashToolbar.toolbarHome.visibility = View.GONE
        (mContext as DashboardActivity).dashView.dashToolbar.toolbarNotification.visibility = View.VISIBLE

        (mContext as DashboardActivity).showHamburgerIcon()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}