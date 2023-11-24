package com.example.fruits.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruits.models.Fruit
import com.example.fruits.repositories.FruitsRepository
import kotlinx.coroutines.launch
import java.util.Locale

class FruitViewModel : ViewModel() {
    private val fruitsRepository = FruitsRepository()

    private val _fruitsLiveData = MutableLiveData<List<Fruit>>()
    val fruitsLiveData: LiveData<List<Fruit>> get() = _fruitsLiveData

    private val _filteredAndSortedFruitsLiveData = MutableLiveData<List<Fruit>>()
    val filteredAndSortedFruitsLiveData: LiveData<List<Fruit>> get() = _filteredAndSortedFruitsLiveData

    private val _fruitsTotalLiveData = MutableLiveData<Int>()
    val fruitsTotalLiveData: LiveData<Int> get() = _fruitsTotalLiveData

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    private val _selectedSortOption = MutableLiveData<String>()
    val selectedSortOption: LiveData<String> get() = _selectedSortOption

    init {
        //defaults
        _searchQuery.value = ""
        _selectedSortOption.value = "sugar"
        loadFruits()
    }

    fun loadFruits() {
        viewModelScope.launch {
            val fruits = fruitsRepository.getFruits()
            _fruitsLiveData.value = fruits
            applyFilterAndSort()
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        applyFilterAndSort()
    }

    fun setSortOption(sortOption: String) {
        _selectedSortOption.value = sortOption
        applyFilterAndSort()
    }

    private fun applyFilterAndSort() {
        val unsortedFruits = _fruitsLiveData.value.orEmpty()
        val query = _searchQuery.value.orEmpty().toLowerCase(Locale.ROOT).trim()

        val filteredFruits = if (query.isEmpty()) {
            unsortedFruits
        } else {
            unsortedFruits.filter { it.name.toLowerCase(Locale.ROOT).contains(query) }
        }

        val sortedAndFilteredFruits = when (_selectedSortOption.value) {
            "sugar" -> filteredFruits.sortedBy { it.nutritions.sugar }
            "fat" -> filteredFruits.sortedBy { it.nutritions.protein}
            "carbohydrates" -> filteredFruits.sortedBy { it.nutritions.protein}
            "protein" -> filteredFruits.sortedBy { it.nutritions.protein }
            "calories" -> filteredFruits.sortedBy { it.nutritions.calories }
            else -> filteredFruits // Default
        }

        _filteredAndSortedFruitsLiveData.value = sortedAndFilteredFruits
        _fruitsTotalLiveData.value = sortedAndFilteredFruits.size
    }
}