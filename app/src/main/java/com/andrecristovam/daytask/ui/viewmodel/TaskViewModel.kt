package com.andrecristovam.daytask.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andrecristovam.daytask.data.constants.TaskConstants
import com.andrecristovam.daytask.data.model.TaskEntity
import com.andrecristovam.daytask.data.repository.TaskRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val mTaskRepository = TaskRepository(application.applicationContext)
    private var date = Calendar.getInstance().time
    private var dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val today = dateTimeFormat.format(date)

    private val mTaskList = MutableLiveData<List<TaskEntity>>()
    val taskList: LiveData<List<TaskEntity>> = mTaskList

    private val mTaskListCompleted = MutableLiveData<List<TaskEntity>>()
    val completedList: LiveData<List<TaskEntity>> = mTaskListCompleted

    private var mSaveTask = MutableLiveData<Boolean>()

    fun laodFragment(filter: Int) {
        if (filter == TaskConstants.FILTER.HOME) {
            viewModelScope.launch {
                mTaskList.value = mTaskRepository.getAll()
            }
        } else if (filter == TaskConstants.FILTER.TODAY) {
            viewModelScope.launch {
                mTaskList.value = mTaskRepository.getToday(today)
            }
        } else if (filter == TaskConstants.FILTER.COMPLETED) {
            viewModelScope.launch{
                mTaskListCompleted.value = mTaskRepository.getCompleted()
            }
        }
    }

    fun save(id: Int, title: String, description: String, date: String, hour: String, completed: Boolean) {
        viewModelScope.launch {
        val task = TaskEntity().apply {
            this.id = id
            this.title = title
            this.description = description
            this.date = date
            this.hour = hour
            this.completed = completed

        }
            if (id != 0 && completed){
                mSaveTask.value = mTaskRepository.update(task)
                mTaskListCompleted.value = mTaskRepository.getCompleted()
            }else {
                mSaveTask.value = mTaskRepository.update(task)
                mTaskListCompleted.value = mTaskRepository.getCompleted()
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            val task = mTaskRepository.get(id)
            mTaskRepository.delete(task)
            mTaskList.value = mTaskRepository.getAll()
            mTaskListCompleted.value = mTaskRepository.getCompleted()
        }
    }
}