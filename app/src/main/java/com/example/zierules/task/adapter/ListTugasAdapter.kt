package com.example.zierules.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zierules.databinding.LayoutItemListprestasiBinding
import com.example.zierules.databinding.LayoutItemListtugasBinding
import com.example.zierules.task.data.DataTask

class ListTugasAdapter
    (private var dataListTugas: ArrayList<DataTask>): RecyclerView.Adapter<ListTugasAdapter.ViewHolder>(){

        inner class ViewHolder(private val binding: LayoutItemListtugasBinding) : RecyclerView.ViewHolder(binding.root) {
            val nama = binding.namaTugas
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemListtugasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListTugas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dataList = dataListTugas[position]

        holder.nama.text = dataList.name.toString()

    }
}