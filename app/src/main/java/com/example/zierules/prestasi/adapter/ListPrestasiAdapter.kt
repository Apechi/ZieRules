package com.example.zierules.prestasi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zierules.databinding.LayoutItemListprestasiBinding

import com.example.zierules.prestasi.data.DataListPrestasi
import com.example.zierules.prestasi.data.ListPrestasi

class ListPrestasiAdapter
    (private var dataListPrestasi: ArrayList<DataListPrestasi>) : RecyclerView.Adapter<ListPrestasiAdapter.ViewHolder>() {

        inner class ViewHolder(private val binding: LayoutItemListprestasiBinding) : RecyclerView.ViewHolder(binding.root) {
            val nama = binding.namaPrestasi
            val point = binding.pointPrestasi
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemListprestasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListPrestasi.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var datalist = dataListPrestasi[position]

        holder.nama.text = datalist.name.toString()
        holder.point.text = datalist.point.toString()
    }
}