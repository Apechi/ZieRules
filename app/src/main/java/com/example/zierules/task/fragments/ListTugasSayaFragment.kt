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
import com.example.zierules.databinding.FragmentListTugasSayaBinding
import com.example.zierules.helper.Constant
import com.example.zierules.helper.PreferenceHelper
import com.example.zierules.task.adapter.ListTugasSayaAdapter
import com.example.zierules.task.data.DataTaskSaya
import com.example.zierules.task.data.ListTugasSaya
import com.google.gson.Gson


class ListTugasSayaFragment : Fragment() {

    lateinit var sharedPref: PreferenceHelper
    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentListTugasSayaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListTugasSayaBinding.inflate(inflater, container, false)

        sharedPref = PreferenceHelper(requireContext())
        recyclerView = binding.rvTugas

        binding.swipeToRefresh.setOnRefreshListener {
            getTugasSaya()
            binding.swipeToRefresh.isRefreshing = false
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getTugasSaya()
    }

    private fun getTugasSaya() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "${MyApplication.BASE_URL}/student/data/task"
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                try {
                    val gson = Gson()
                    val listTugasSaya = gson.fromJson(response.toString(), ListTugasSaya::class.java)
                    val dataTugasSaya = DataTaskSaya::class.java
                    val adapter = ListTugasSayaAdapter(listTugasSaya.dataTask)
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    var tugasSelesai: Int = 0
                    for (i in listTugasSaya.dataTask) {
                        if (i.finished) {
                            tugasSelesai++
                        }
                    }
                    binding.totalTugas.text = tugasSelesai.toString()
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