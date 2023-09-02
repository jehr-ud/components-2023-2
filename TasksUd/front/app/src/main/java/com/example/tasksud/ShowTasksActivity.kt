package com.example.tasksud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class ShowTasksActivity : AppCompatActivity() {
    val taskManager = TaskManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tasks)

        val btnAddTask: Button = findViewById(R.id.addTask)

        var tasks = taskManager.getAll()

        for (task in tasks){
            print("task in the app: ")
            Log.d("savetask", task.toString())
        }

        btnAddTask.setOnClickListener{
            val intent = Intent(this, RegisterTaskActivity::class.java)
            startActivity(intent)
        }
    }
}