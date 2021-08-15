package com.andrecristovam.daytask.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andrecristovam.daytask.data.model.TaskEntity
import com.andrecristovam.daytask.data.repository.TaskRepository
import kotlinx.coroutines.launch

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private val mTaskRepository: TaskRepository = TaskRepository(mContext)

    private var mSaveTask = MutableLiveData<Boolean>()
    val saveTask: LiveData<Boolean> = mSaveTask

    private var mTask = MutableLiveData<TaskEntity>()
    val task: LiveData<TaskEntity> = mTask

    fun save(id: Int, title: String, description: String, date: String, hour: String) {
        viewModelScope.launch {
            val task = TaskEntity().apply {
                this.id = id
                this.title = title
                this.description = description
                this.date = date
                this.hour = hour
            }
            if (id == 0) {
                mSaveTask.value = mTaskRepository.save(task)
            } else {
                mSaveTask.value = mTaskRepository.save(task)
            }
        }
    }

    fun load(id: Int) {
        viewModelScope.launch {
            mTask.value = mTaskRepository.get(id)
        }
    }
}