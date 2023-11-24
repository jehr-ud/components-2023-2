package com.example.fruits.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruits.R
import com.example.fruits.adapters.FruitsListAdapter
import com.example.fruits.databinding.ActivityMainBinding
import com.example.fruits.viewmodels.FruitViewModel


class MainActivity : ComponentActivity(){
    private lateinit var binding: ActivityMainBinding
    private var fruitsViewModel: FruitViewModel? = null
    private var fruitsAdapter: FruitsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.fruitRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fruitsAdapter = FruitsListAdapter { selectedFruit ->
            // show details fruit
            val intent = Intent(this, FruitDetailActivity::class.java)
            intent.putExtra(FruitDetailActivity.EXTRA_FRUIT, selectedFruit)
            startActivity(intent)
        }

        recyclerView.adapter = fruitsAdapter

        val sortOptionsAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.sort_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.orderSpinner.adapter = adapter
        }

        fruitsViewModel = ViewModelProvider(this)[FruitViewModel::class.java]

        fruitsViewModel?.filteredAndSortedFruitsLiveData?.observe(this) { fruits ->
            Toast.makeText(this, "Data was changed!", Toast.LENGTH_LONG)
            fruitsAdapter?.setFruits(fruits)
        }

        fruitsViewModel?.fruitsTotalLiveData?.observe(this) { total ->
            binding.textViewTotalFruits.text = "Total Fruits: $total"
        }

        listenersOfViews()
        fruitsViewModel?.setSortOption(sortOptionsAdapter.getItem(0).toString())
    }

    private fun listenersOfViews(){
        binding.orderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedProperty = parent.getItemAtPosition(position).toString()
                fruitsViewModel?.setSortOption(selectedProperty)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        binding.editTextFruitName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                fruitsViewModel?.setSearchQuery(searchText)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}