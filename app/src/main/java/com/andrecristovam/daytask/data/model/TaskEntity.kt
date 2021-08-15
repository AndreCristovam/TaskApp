package com.andrecristovam.daytask.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_task")
class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "date")
    var date: String = ""

    @ColumnInfo(name = "hour")
    var hour: String = ""

    @ColumnInfo(name = "completed")
    var completed: Boolean = false
}