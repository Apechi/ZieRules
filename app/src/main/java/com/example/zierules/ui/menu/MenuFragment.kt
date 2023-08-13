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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}