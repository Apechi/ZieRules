package com.example.zierules.prestasi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zierules.MyApplication
import com.example.zierules.R
import com.example.zierules.databinding.FragmentListPrestasiBinding
import com.example.zierules.databinding.LayoutItemListprestasiBinding
import com.example.zierules.helper.Constant
import com.example.zierules.helper.PreferenceHelper
import com.example.zierules.pelanggaran.adapter.DataPelanggaranSayaAdapter
import com.example.zierules.pelanggaran.data.PelanggaranSayaData
import com.example.zierules.prestasi.adapter.ListPrestasiAdapter
import com.example.zierules.prestasi.data.ListPrestasi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ListPrestasiFragment : Fragment() {

    lateinit var recycleView: RecyclerView
    lateinit var sharedPref: PreferenceHelper
    lateinit var binding: FragmentListPrestasiBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListPrestasiBinding.inflate(inflater, container, false)

        sharedPref = PreferenceHelper(requireContext())
        recycleView = binding.rvPrestasi

        binding.swipeToRefresh.setOnRefreshListener {
            getListPrestasi()
            binding.swipeToRefresh.isRefreshing = false
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getListPrestasi()
    }


    private fun getListPrestasi() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "${MyApplication.BASE_URL}/student/list/achievment"
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                try {
                    val gson = Gson()
                    val listPrestasi = gson.fromJson(response.toString(), ListPrestasi::class.java)
                    val adapter = ListPrestasiAdapter(listPrestasi.achievments)
                    recycleView.layoutManager = LinearLayoutManager(requireContext())
                    recycleView.adapter = adapter
                    adapter.notifyDataSetChanged()
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

    fun parseJsonArray(jsonArray: String): ArrayList<ListPrestasi> {
        val gson = Gson()
        val typeToken = object : TypeToken<ArrayList<ListPrestasi>>() {}.type
        return gson.fromJson(jsonArray, typeToken)
    }


}