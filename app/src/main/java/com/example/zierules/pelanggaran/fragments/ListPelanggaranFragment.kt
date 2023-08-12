package com.example.zierules.pelanggaran.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zierules.MyApplication
import com.example.zierules.R
import com.example.zierules.databinding.FragmentListPelanggaranBinding
import com.example.zierules.helper.Constant
import com.example.zierules.helper.PreferenceHelper
import com.example.zierules.pelanggaran.adapter.ListPelanggaranAdapter
import com.example.zierules.pelanggaran.data.ListDataViolation
import com.example.zierules.pelanggaran.data.ListPelanggaranData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ListPelanggaranFragment : Fragment() {

    lateinit private var _binding: FragmentListPelanggaranBinding
    lateinit private var sharePref: PreferenceHelper
    lateinit private var recycleView: RecyclerView
    lateinit private var adapter: ListPelanggaranAdapter

    private var pelanggaranList = ArrayList<ListDataViolation>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListPelanggaranBinding.inflate(inflater, container, false)

        sharePref = PreferenceHelper(requireContext())
        recycleView = _binding.rvPelanggaran

        val swipeRefresh = _binding.swipeToRefresh

        adapter = ListPelanggaranAdapter(pelanggaranList)


        swipeRefresh.setOnRefreshListener {
            getListPelanggaranData()
            swipeRefresh.isRefreshing = false
        }

        return _binding.root
    }


    override fun onStart() {
        super.onStart()
        getListPelanggaranData()
    }


    private fun getListPelanggaranData() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "${MyApplication.BASE_URL}/student/list/violation"
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val gson = Gson()
                    val listPelanggaran =  gson.fromJson(response.toString(), ListPelanggaranData::class.java)
                    val adapter = ListPelanggaranAdapter(listPelanggaran.dataViolation)
                    Log.d("tes", listPelanggaran.toString())
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
                headers["Authorization"] = "Bearer ${sharePref.getString(Constant.PREF_TOKEN.toString())}"
                return headers
            }
        }
        queue.add(jsonObjectRequest)

    }





}