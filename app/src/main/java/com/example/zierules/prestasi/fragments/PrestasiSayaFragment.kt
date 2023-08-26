package com.example.zierules.prestasi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zierules.MyApplication
import com.example.zierules.R
import com.example.zierules.databinding.FragmentListPrestasiBinding
import com.example.zierules.databinding.FragmentPrestasiSayaBinding
import com.example.zierules.helper.Constant
import com.example.zierules.helper.PreferenceHelper
import com.example.zierules.pelanggaran.adapter.DataPelanggaranSayaAdapter
import com.example.zierules.pelanggaran.data.PelanggaranSayaData
import com.example.zierules.prestasi.adapter.PrestasiSayaAdapter
import com.example.zierules.prestasi.data.PrestasiSaya
import com.google.gson.Gson


class PrestasiSayaFragment : Fragment() {

    lateinit var recycleView: RecyclerView
    lateinit var sharedPref: PreferenceHelper
    lateinit var binding: FragmentPrestasiSayaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrestasiSayaBinding.inflate(inflater, container, false)

        sharedPref = PreferenceHelper(requireContext())
        recycleView = binding.rvPrestasi

        binding.swipeToRefresh.setOnRefreshListener {
            getDataPrestasiSaya()
            binding.swipeToRefresh.isRefreshing = false
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getDataPrestasiSaya()

    }

    private fun getDataPrestasiSaya() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "${MyApplication.BASE_URL}/student/data/achievment"
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                try {
                    val gson = Gson()
                    val DataPrestasiSaya = gson.fromJson(response.toString(), PrestasiSaya::class.java)

                    val adapter = PrestasiSayaAdapter(DataPrestasiSaya.dataAchievments)
                    recycleView.layoutManager = LinearLayoutManager(requireContext())
                    recycleView.adapter = adapter
                    adapter.notifyDataSetChanged()

                    //PUT POINT DATA TO UI
                    binding.totalPoint.text = DataPrestasiSaya.totalPoint.toString()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
//                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${sharedPref.getString(Constant.PREF_TOKEN.toString())}"
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }


}