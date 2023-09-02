package com.example.tasksud

data class Task (
    val name: String,
    val description: String,
    val createAt: String,
    val dueAt: String,
    val state: TaskState){
}