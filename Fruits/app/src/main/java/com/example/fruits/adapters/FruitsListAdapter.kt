package com.example.fruits.adapters

import com.example.fruits.models.Fruit
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fruits.R


class FruitsListAdapter(
    private val onItemClick: (Fruit) -> Unit
) : RecyclerView.Adapter<FruitsListAdapter.ViewHolder>() {

    private var fruits: List<Fruit> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fruit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruits[position]
        holder.bind(fruit)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(fruit)
        }
    }

    override fun getItemCount(): Int {
        return fruits.size
    }

    fun setFruits(fruits: List<Fruit>) {
        this.fruits = fruits
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewFruitName: TextView = itemView.findViewById(R.id.textViewFruitName)
        fun bind(fruit: Fruit) {
            with(itemView) {
                textViewFruitName.text = fruit.name
            }
        }
    }
}