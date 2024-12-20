package com.example.xst.utils

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.example.modernretail.databinding.DialogOkBinding
import com.example.modernretail.others.Pref


class DialogOk(var title:String,var message: String,var listner: OnClick) : DialogFragment(), View.OnClickListener {
    private lateinit var mContext: Context

    private var binding: DialogOkBinding? = null
    private val dialogView get() = binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogOkBinding.inflate(inflater, container, false)
        dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(false)    //restrict dialog close on touch screen outside dialog
        dialog?.setCancelable(true)//restrict dialog close on touch back button ** setCancelable is superior to setCanceledOnTouchOutside
        return dialogView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x

        val newWidth = width * .85
        val layoutParams: ViewGroup.LayoutParams = dialogView.cvRoot.layoutParams
        layoutParams.width = newWidth.toInt()

        process()
    }

    fun process() {
        dialogView.tvMessage.text = message
        dialogView.tvTitle.text = title
        dialogView.btnOk.setOnClickListener(this)
        dialogView.ivCross.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            dialogView.btnOk.id -> {
                dismiss()
                listner.onOkClick()
            }
            dialogView.ivCross.id -> {
                dismiss()
            }
        }
    }

    interface OnClick {
        fun onOkClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}