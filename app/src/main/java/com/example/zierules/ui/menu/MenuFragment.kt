package com.example.zierules.ui.menu

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zierules.R
import com.example.zierules.databinding.FragmentMenuBinding
import com.example.zierules.pelanggaran.PelanggaranActivity
import com.example.zierules.prestasi.PrestasiActivity
import com.example.zierules.task.TaskActivity

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(MenuViewModel::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.toPelanggaran.setOnClickListener {
            changeActivity(0)
        }

        binding.toPrestasi.setOnClickListener {
            changeActivity(1)
        }

        binding.toTugas.setOnClickListener {
            changeActivity(2)
        }


        return root

    }


    private fun changeActivity(menu: Int) {

        when(menu) {
            0 -> startActivity(Intent(requireContext(), PelanggaranActivity::class.java))
            1 -> startActivity(Intent(requireContext(), PrestasiActivity::class.java))
            2 -> startActivity(Intent(requireContext(), TaskActivity::class.java))

        }
    }

    override fun onStart() {
        super.onStart()
        if (!isNetworkAvailable(requireContext())) {
            showIntenetDialog()
            return
        }
    }


    private fun showIntenetDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setView(R.layout.check_internet)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}