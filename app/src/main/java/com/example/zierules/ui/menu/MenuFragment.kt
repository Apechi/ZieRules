package com.example.zierules.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zierules.databinding.FragmentMenuBinding
import com.example.zierules.pelanggaran.PelanggaranActivity

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


        return root

    }

    private fun changeActivity(menu: Int) {
        val pelanggaranIntent = Intent(requireContext(), PelanggaranActivity::class.java)
        when(menu) {
            0 -> startActivity(pelanggaranIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}