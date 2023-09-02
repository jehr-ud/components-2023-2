package com.example.tasksud

object TaskManager {
  private val listTasks: MutableList<Task> = mutableListOf()

  fun add(task: Task){
    listTasks.add(task)
  }

  fun getAll(): MutableList<Task>{
    return listTasks
  }
}