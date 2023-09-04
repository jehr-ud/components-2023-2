package com.example.tasksud

import java.io.Serializable

data class Task(
    val name: String,
    val description: String,
    val createAt: String,
    val dueAt: String,
    val state: TaskState): Serializable