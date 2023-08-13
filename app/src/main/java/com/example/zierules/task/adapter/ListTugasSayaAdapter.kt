package com.example.zierules.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zierules.databinding.LayoutItemListtugasBinding
import com.example.zierules.databinding.LayoutItemTugassayaBinding
import com.example.zierules.task.data.DataTaskSaya

data class ListTugasSayaAdapter
    (private var dataListTugasSaya: ArrayList<DataTaskSaya>): RecyclerView.Adapter<ListTugasSayaAdapter.ViewHolder>() {

        inner class ViewHolder(private var binding: LayoutItemTugassayaBinding): RecyclerView.ViewHolder(binding.root) {
            val nameTugas = binding.namaTugas
            val kelas = binding.kelas
            val teacher = binding.teacher
            val date = binding.date
            val description = binding.deskripsi
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemTugassayaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListTugasSaya.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataList = dataListTugasSaya[position]

        holder.nameTugas.text = dataList.name.toString()
        holder.kelas.text = dataList.`class`.toString()
        holder.teacher.text = dataList.teacher.toString()
        holder.date.text = dataList.date.toString()
        holder.description.text = dataList.description.toString()
    }
}
