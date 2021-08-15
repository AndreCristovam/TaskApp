package com.andrecristovam.daytask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andrecristovam.daytask.data.model.TaskEntity
import com.andrecristovam.daytask.databinding.ItemTaskBinding
import com.andrecristovam.daytask.ui.listener.TaskListener
import com.andrecristovam.daytask.ui.viewholder.TaskViewHolder

class TaskAdapter : RecyclerView.Adapter<TaskViewHolder>() {

    private var mTaskList: List<TaskEntity> = arrayListOf()
    private var mCompletedList: List<TaskEntity> = arrayListOf()
    private lateinit var mListener: TaskListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding: ItemTaskBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, mListener)
    }

    override fun getItemCount(): Int {
        return mTaskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(mTaskList[position])
    }

    fun updateTask(list: List<TaskEntity>) {
        mTaskList = list
        mCompletedList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: TaskListener) {
        mListener = listener
    }
}