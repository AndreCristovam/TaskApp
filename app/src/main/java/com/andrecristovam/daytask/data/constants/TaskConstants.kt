package com.andrecristovam.daytask.data.constants

class TaskConstants private constructor() {

    companion object {
        const val TASKID = "taskID"
    }

    object FILTER {
        const val HOME = 0
        const val TODAY = 1
        const val COMPLETED = 2
    }
}