package com.example.zierules.prestasi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zierules.databinding.LayoutItemListprestasiBinding
import com.example.zierules.databinding.LayoutItemPrestasisayaBinding
import com.example.zierules.prestasi.data.DataAchievement
import com.example.zierules.prestasi.data.PrestasiSaya

class PrestasiSayaAdapter
    (private var dataListPrestasiSaya: ArrayList<DataAchievement>) : RecyclerView.Adapter<PrestasiSayaAdapter.ViewHolder>(){

        inner class ViewHolder (private val binding: LayoutItemPrestasisayaBinding) : RecyclerView.ViewHolder(binding.root) {
            var nama_prestasi = binding.namaPrestasi
            var nama_guru = binding.namaGuru
            var point = binding.pointPrestasi
            var deskripsi = binding.deskripsi
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemPrestasisayaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListPrestasiSaya.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listPrestasiSaya = dataListPrestasiSaya[position]

        holder.nama_prestasi.text = listPrestasiSaya.name
        holder.nama_guru.text = "Oleh: ${listPrestasiSaya.teacher}"
        holder.point.text = listPrestasiSaya.point.toString()
        holder.deskripsi.text = "Deskripsi: ${listPrestasiSaya.description.toString()}"
    }
}