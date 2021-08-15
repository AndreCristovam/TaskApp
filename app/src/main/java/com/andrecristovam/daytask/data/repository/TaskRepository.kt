package com.andrecristovam.daytask.data.repository

import android.content.Context
import com.andrecristovam.daytask.data.db.AppDatabase
import com.andrecristovam.daytask.data.model.TaskEntity

class TaskRepository(context: Context) {

    private val mDatabase = AppDatabase.getDatabase(context).taskDao()

    suspend fun save(taskEntity: TaskEntity): Boolean {
        return mDatabase.save(taskEntity) > 0
    }

    suspend fun delete(taskEntity: TaskEntity) {
        return mDatabase.delete(taskEntity)
    }

    suspend fun update(taskEntity: TaskEntity): Boolean {
        return mDatabase.update(taskEntity) > 0
    }

    suspend fun getAll(): List<TaskEntity> {
        return mDatabase.getAll()
    }

    suspend fun get(id: Int): TaskEntity {
        return mDatabase.get(id)
    }

    suspend fun getToday(date: String): List<TaskEntity>{
    return  mDatabase.getToday(date)
    }

    suspend fun getCompleted(): List<TaskEntity> {
        return mDatabase.getCompleted()
    }
}