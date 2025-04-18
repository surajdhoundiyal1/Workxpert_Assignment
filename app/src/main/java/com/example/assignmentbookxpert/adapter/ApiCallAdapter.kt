package com.example.assignmentbookxpert.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentbookxpert.databinding.ApiCallRecyclerViewBinding
import com.example.assignmentbookxpert.entitty.ApiEntity
import com.example.assignmentbookxpert.model.ApiResponseData

class ApiCallAdapter() : RecyclerView.Adapter<ApiCallAdapter.MyViewHolder>() {         //private val listener: OnClickListener

    private val masterList = mutableListOf<ApiResponseData>()

    inner class MyViewHolder(private val binding: ApiCallRecyclerViewBinding)  :RecyclerView.ViewHolder(binding.root) {
//        init {
//            binding.apply {
//
//                root.setOnLongClickListener() {
//                    val position = adapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        val article = masterList[position]
//                        listener.onMasterClick(article)
//                    }
//                    true
//                }
//            }
//        }
        fun bind(apiData: ApiResponseData){
            binding.tvId.text = apiData.id
            binding.tvName.text = apiData.name
//            binding.tvData.text = apiData.data.toString()
             binding.tvData.text = apiData.data?.entries?.joinToString("\n") { "${it.key}: ${it.value}" } ?: "No data available"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ApiCallRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return masterList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(masterList[position])
    }

    fun submitList(newList: List<ApiResponseData>) {
        masterList.clear()
        masterList.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onMasterClick(apiData:ApiEntity)
    }

}