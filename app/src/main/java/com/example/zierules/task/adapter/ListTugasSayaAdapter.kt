package com.example.zierules.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.zierules.databinding.LayoutItemTugassayaBinding
import com.example.zierules.databinding.LayoutItemTugassayaSelesaiBinding
import com.example.zierules.task.data.DataTaskSaya

 class ListTugasSayaAdapter
    (private var dataListTugasSaya: ArrayList<DataTaskSaya>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     private val VIEW_TYPE_TASK_WITH_RESULT = 0
     private val VIEW_TYPE_TASK_WITHOUT_RESULT = 1

        inner class TaskWithResultViewHolder(private var binding: LayoutItemTugassayaSelesaiBinding): RecyclerView.ViewHolder(binding.root) {
            private val namaTugas = binding.namaTugas
            val kelas = binding.kelas
            val desc = binding.deskripsi
            val teacher = binding.teacher
            val date = binding.date

            fun bind(task: DataTaskSaya) {
                namaTugas.text = task.name
                kelas.text = task.`class`
                desc.text = task.result.description
                teacher.text = task.result.teacher
                date.text = task.result.date
            }
        }
        inner class TaskWithoutResultViewHolder(private var binding: LayoutItemTugassayaBinding): RecyclerView.ViewHolder(binding.root) {
            val nameTugas = binding.namaTugas
            val kelas = binding.kelas
            val description = binding.deskripsi

            fun bind(task: DataTaskSaya) {
                nameTugas.text = task.name
                kelas.text = task.`class`
                description.text = task.description
            }
        }

     override fun getItemViewType(position: Int): Int {
         val task = dataListTugasSaya[position]
         return if (task.result != null) {
            VIEW_TYPE_TASK_WITH_RESULT
         }else {
            VIEW_TYPE_TASK_WITHOUT_RESULT
         }
     }

    override fun getItemCount(): Int {
        return dataListTugasSaya.size
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         val inflater = LayoutInflater.from(parent.context)

         return when(viewType) {
             VIEW_TYPE_TASK_WITH_RESULT -> {
                 val binding = LayoutItemTugassayaSelesaiBinding.inflate(inflater, parent, false)
                 TaskWithResultViewHolder(binding)
             } else -> {
                 val binding = LayoutItemTugassayaBinding.inflate(inflater, parent, false)
                 TaskWithoutResultViewHolder(binding)
             }
         }
     }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataList = dataListTugasSaya[position]

        when (holder.itemViewType) {
         VIEW_TYPE_TASK_WITH_RESULT -> {
             val taskWithResultHolder = holder as TaskWithResultViewHolder
             taskWithResultHolder.bind(dataList)
         }
            VIEW_TYPE_TASK_WITHOUT_RESULT -> {
                val taskWithoutResultHolder = holder as TaskWithoutResultViewHolder
                taskWithoutResultHolder.bind(dataList)
            }
        }
    }
}
