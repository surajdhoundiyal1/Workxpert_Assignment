package com.example.assignmentbookxpert.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentbookxpert.R
import com.example.assignmentbookxpert.databinding.DashboardViewBinding

class DashboardAdapter(val viewList: MutableList<DashboardModel>, val listener: DashboardClickListener) : RecyclerView.Adapter<DashboardAdapter.MyViewHolder>() {
    class MyViewHolder(binding: DashboardViewBinding) : RecyclerView.ViewHolder(binding.root)  {
        val imageview: ImageView = itemView.findViewById(R.id.image_view)
        val textView : TextView = itemView.findViewById(R.id.tv_title)
        val consDashboard: ConstraintLayout = itemView.findViewById(R.id.cons_dash_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardAdapter.MyViewHolder {
        return MyViewHolder(DashboardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DashboardAdapter.MyViewHolder, position: Int) {
        val item = viewList.get(position)
        holder.textView.text = item.name
        holder.imageview.setImageResource(item.image)
        holder.consDashboard.setOnClickListener {
            listener.onItemClicked(item)
        }

    }

    override fun getItemCount(): Int {
        return viewList.size
    }

}