package com.andrecristovam.daytask.data.dao

import androidx.room.*
import com.andrecristovam.daytask.data.model.TaskEntity


@Dao
interface TaskDAO {

    @Query("SELECT * FROM table_task")
    suspend fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM table_task WHERE id = :id")
   suspend fun get(id: Int): TaskEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(taskEntity: TaskEntity) : Long

    @Delete
    suspend fun delete(taskEntity: TaskEntity)

    @Update
    suspend fun update(taskEntity: TaskEntity) : Int

    @Query("SELECT * FROM table_task WHERE date = :date")
    suspend fun getToday(date: String): List<TaskEntity>

    @Query("SELECT * FROM table_task WHERE completed = 1")
    suspend fun getCompleted(): List<TaskEntity>

}