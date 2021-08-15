package com.andrecristovam.daytask.ui.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.andrecristovam.daytask.R
import com.andrecristovam.daytask.data.model.TaskEntity
import com.andrecristovam.daytask.databinding.ItemTaskBinding
import com.andrecristovam.daytask.ui.listener.TaskListener

class TaskViewHolder(val binding: ItemTaskBinding, private val listener: TaskListener) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(task: TaskEntity) {

        with(binding) {
            tvTitle.text = task.title
            tvDescription.text = task.description
            tvDate.text = task.date
            tvHour.text = task.hour

        }

        if (task.completed){
           binding.ivDone.setImageResource(R.drawable.ic_done)
        }

        binding.ivDone.setOnClickListener {
            if (!task.completed) {
                task.completed = true
                if (task.completed) binding.ivDone.setImageResource(R.drawable.ic_done)
            } else {
                if (task.completed) {
                    task.completed = false
                    if (!task.completed) binding.ivDone.setImageResource(R.drawable.ic_todo)
                }
            }

            listener.onUpdate(
                task.id,
                task.title,
                task.description,
                task.date,
                task.hour,
                task.completed
            )
        }

        binding.mcvContent.setOnClickListener {
            listener.onClick(task.id)
        }

        binding.mcvContent.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remove_task)
                .setMessage(R.string.want_to_remove)
                .setPositiveButton(R.string.remove) { dialog, which ->
                    listener.onDelete(task.id)
                }
                .setNeutralButton(R.string.cancel, null)
                .show()
            true
        }
    }
}