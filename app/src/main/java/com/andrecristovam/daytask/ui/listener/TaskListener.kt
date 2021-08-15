package com.andrecristovam.daytask.ui.listener

interface TaskListener {
    fun onClick(id: Int)
    fun onDelete(id: Int)
    fun onUpdate(id: Int, title: String, description: String, date: String, hour: String, completed: Boolean)
}