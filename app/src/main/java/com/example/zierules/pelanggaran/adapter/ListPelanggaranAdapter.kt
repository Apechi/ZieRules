package com.example.zierules.pelanggaran.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zierules.databinding.LayoutItemListpelanggaranBinding
import com.example.zierules.pelanggaran.data.ListPelanggaranData

class ListPelanggaranAdapter(
    private  var dataListPelanggaran: ArrayList<ListPelanggaranData>) :
    RecyclerView.Adapter<ListPelanggaranAdapter.MyViewHolder>() {

   inner class MyViewHolder(private val binding: LayoutItemListpelanggaranBinding) : RecyclerView.ViewHolder(binding.root) {
        val nama_pelanggaran = binding.namaPelanggaran
        val point_pelanggaran = binding.pointPelanggaran
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutItemListpelanggaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListPelanggaran.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listPelanggaran = dataListPelanggaran[position]

        holder.nama_pelanggaran.text = listPelanggaran.name
        holder.point_pelanggaran.text = listPelanggaran.point.toString()


    }

}