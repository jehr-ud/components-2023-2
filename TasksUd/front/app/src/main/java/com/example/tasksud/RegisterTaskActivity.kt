package com.example.tasksud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RegisterTaskActivity : AppCompatActivity() {
    val taskManager = TaskManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_task)

        val button = findViewById<Button>(R.id.btnAddNewTask)

        button.setOnClickListener{
            val newTask = Task(
                "Proyecto 1",
                "Realizar proyeto de serpientes y escaleras",
                "2023-08-28",
                "2023-09-15",
                TaskState.INIT
            )
            taskManager.add(newTask)

            val intent = Intent(this, ShowTasksActivity::class.java)
            startActivity(intent)
        }
    }
}