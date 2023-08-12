package com.example.zierules.pelanggaran.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zierules.databinding.LayoutItemPelanggaransayaBinding
import com.example.zierules.pelanggaran.data.DataViolation
import com.example.zierules.pelanggaran.data.PelanggaranSayaData

class DataPelanggaranSayaAdapter(
    private var dataListPelanggaranSaya: ArrayList<DataViolation>
): RecyclerView.Adapter<DataPelanggaranSayaAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: LayoutItemPelanggaransayaBinding): RecyclerView.ViewHolder(binding.root) {
        var nama_pelanggaran = binding.namaPelanggaran
        var nama_guru = binding.namaGuru
        var point = binding.pointPelanggaran

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemPelanggaransayaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataListPelanggaranSaya.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listPelanggaranSaya = dataListPelanggaranSaya[position]

        holder.nama_pelanggaran.text = listPelanggaranSaya.name
        holder.nama_guru.text = "Oleh: ${listPelanggaranSaya.teacher}"
        holder.point.text = listPelanggaranSaya.point.toString()

    }
}