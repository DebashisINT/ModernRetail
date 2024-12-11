package com.example.modernretail.compFeed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.modernretail.DashboardActivity
import com.example.modernretail.databinding.FragComplaintFeedbackBinding

class ComplaintFeedback: Fragment(), View.OnClickListener {
    private var binding: FragComplaintFeedbackBinding? = null
    private val comFeedView get() = binding!!

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragComplaintFeedbackBinding.inflate(inflater, container, false)
        return comFeedView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        proceed()
    }

    private fun proceed() {

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        (mContext as DashboardActivity).toolbarTitle.text = "Complaint & Feedback"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}