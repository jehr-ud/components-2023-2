package com.example.fruits.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.fruits.databinding.ActivityFruitDetailBinding
import com.example.fruits.models.Fruit

class FruitDetailActivity : ComponentActivity() {

    private lateinit var binding: ActivityFruitDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFruitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fruit = intent.getParcelableExtra<Fruit>(EXTRA_FRUIT)

        binding.textViewDetails.text = """ 
            Sugar: ${fruit?.nutritions?.sugar}
            Fat: ${fruit?.nutritions?.fat}
            Calories: ${fruit?.nutritions?.calories}
            Carbohydrates: ${fruit?.nutritions?.carbohydrates}
        """.trimIndent()
    }

    companion object {
        const val EXTRA_FRUIT = "extra_fruit"
    }
}