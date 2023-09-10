package com.example.tasksud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ShowTasksActivity : AppCompatActivity() {
    val taskManager = TaskManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tasks)

        val btnAddTask: Button = findViewById(R.id.addTask)
        val layoutTasks: LinearLayout = findViewById(R.id.contentTasks)

        var tasks = taskManager.getAll()

        for (task in tasks){
            val layoutTask = LinearLayout(this)

            val textViewTask =  TextView(this).apply{
                text = task.name
            }

            val buttonTask =  Button(this)
            buttonTask.text = "Ver"
            buttonTask.setOnClickListener{
                val intent = Intent(this, ShowDetailActivity::class.java)
                intent.putExtra("task", task)
                startActivity(intent)
            }

            layoutTask.addView(textViewTask)
            layoutTask.addView(buttonTask)

            layoutTasks.addView(layoutTask)
        }

        btnAddTask.setOnClickListener{
            val intent = Intent(this, RegisterTaskActivity::class.java)
            startActivity(intent)
        }
    }
}