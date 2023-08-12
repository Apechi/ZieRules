package com.example.zierules.pelanggaran.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zierules.MyApplication
import com.example.zierules.R
import com.example.zierules.databinding.FragmentPelanggaranSayaBinding
import com.example.zierules.helper.Constant
import com.example.zierules.helper.PreferenceHelper
import com.example.zierules.pelanggaran.adapter.DataPelanggaranSayaAdapter
import com.example.zierules.pelanggaran.adapter.ListPelanggaranAdapter
import com.example.zierules.pelanggaran.data.PelanggaranSayaData
import com.google.gson.Gson


class PelanggaranSayaFragment : Fragment() {

    lateinit var binding: FragmentPelanggaranSayaBinding
    lateinit var sharedPref: PreferenceHelper
    lateinit private var recycleView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPelanggaranSayaBinding.inflate(inflater, container, false)

        sharedPref = PreferenceHelper(requireContext())

        recycleView = binding.rvPelanggaran

        binding.swipeToRefresh.setOnRefreshListener {
            getDataPelanggaranSaya()
            binding.swipeToRefresh.isRefreshing = false
        }

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        getDataPelanggaranSaya()
    }

    private fun getDataPelanggaranSaya() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "${MyApplication.BASE_URL}/student/data/violation"
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                try {
                    val gson = Gson()
                    val DataPelanggaranSaya = gson.fromJson(response.toString(), PelanggaranSayaData::class.java)

                    val adapter = DataPelanggaranSayaAdapter(DataPelanggaranSaya.dataViolation)
                    recycleView.layoutManager = LinearLayoutManager(requireContext())
                    recycleView.adapter = adapter
                    adapter.notifyDataSetChanged()

                    //PUT POINT DATA TO UI
                    binding.totalPoint.text = DataPelanggaranSaya.totalPoint.toString()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
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

