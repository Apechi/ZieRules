package com.example.zierules.task.fragments

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
import com.example.zierules.databinding.FragmentListTaskBinding
import com.example.zierules.helper.Constant
import com.example.zierules.helper.PreferenceHelper
import com.example.zierules.prestasi.adapter.ListPrestasiAdapter
import com.example.zierules.prestasi.data.ListPrestasi
import com.example.zierules.task.adapter.ListTugasAdapter
import com.example.zierules.task.data.ListTugasData
import com.google.gson.Gson


class ListTugasFragment : Fragment() {

    lateinit var sharedPref: PreferenceHelper
    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentListTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListTaskBinding.inflate(inflater, container, false)

        sharedPref = PreferenceHelper(requireContext())
        recyclerView = binding.rvTugas

        binding.swipeToRefresh.setOnRefreshListener {
            getListTugas()
            binding.swipeToRefresh.isRefreshing = false
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getListTugas()
    }

    private fun getListTugas() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "${MyApplication.BASE_URL}/student/list/task"
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                try {
                    val gson = Gson()
                    val listTugas = gson.fromJson(response.toString(), ListTugasData::class.java)
                    val adapter = ListTugasAdapter(listTugas.dataTask)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = adapter
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





}