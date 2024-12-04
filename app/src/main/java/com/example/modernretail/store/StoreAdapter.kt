package com.example.modernretail.store

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.modernretail.R
import com.example.modernretail.database.StoreEntity
import com.example.modernretail.databinding.RowStoreBinding

class StoreAdapter(val mContext: Context, val listner:OnClick):
    PagingDataAdapter<StoreEntity, StoreAdapter.StoreViewHolder>(DiffCallBack()) {

    var shakeAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_shake)

    class DiffCallBack: DiffUtil.ItemCallback<StoreEntity>(){
        override fun areItemsTheSame(oldItem: StoreEntity, newItem: StoreEntity): Boolean = oldItem.store_id == newItem.store_id
        override fun areContentsTheSame(oldItem: StoreEntity, newItem: StoreEntity): Boolean = oldItem == newItem
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        try {
            val item = getItem(position)
            if(item!=null){
                holder.bind(item)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = RowStoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoreViewHolder(binding)
    }

    inner class StoreViewHolder(val binding: RowStoreBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:StoreEntity){
            try {
                binding.tvStoreName.text = item.store_name
                binding.tvStoreAddress.text = item.store_address
                binding.tvStoreContactName.text = item.store_contact_name
                binding.tvStoreContactNumber.text = item.store_contact_number
                binding.tvStoreWhatsapp.text = item.store_contact_whatsapp
                binding.tvStoreEmail.text = item.store_contact_email
                binding.tvStoreType.text = item.store_type
                binding.tvStoreSizeArea.text = item.store_size_area

                if(item.isUploaded){
                    binding.ivSyncStatus.setImageResource(R.drawable.ic_sync)
                }else{
                    binding.ivSyncStatus.setImageResource(R.drawable.ic_sync_na)
                }

                binding.ivContactNumber.startAnimation(shakeAnimation)

                binding.ivContactNumber.setOnClickListener {
                    listner.onCallCLick(item)
                }
                binding.ivSyncStatus.setOnClickListener {
                    listner.onSyncCLick(item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    interface OnClick{
        fun onCallCLick(obj:StoreEntity)
        fun onSyncCLick(obj:StoreEntity)
    }
}