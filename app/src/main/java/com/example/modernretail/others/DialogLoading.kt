package com.example.modernretail.others

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.modernretail.R
import android.view.animation.AnimationUtils
import android.widget.TextView

@SuppressLint("StaticFieldLeak")
object DialogLoading: DialogFragment() {
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(false)    //restrict dialog close on touch screen outside dialog
        dialog?.setCancelable(false)//restrict dialog close on touch back button ** setCancelable is superior to setCanceledOnTouchOutside
        val v = inflater.inflate(R.layout.dialog_loading,container,false)

        val shakeAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_left_right)
        var tvLoading = v.findViewById<TextView>(R.id.tv_loading)
        tvLoading.startAnimation(shakeAnimation)

        dialog!!.show()
        return v
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}