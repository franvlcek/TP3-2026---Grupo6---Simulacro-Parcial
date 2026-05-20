package com.ort.quotesappgrupo6.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ort.quotesappgrupo6.data.local.QuoteEntity
import com.ort.quotesappgrupo6.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _favoriteQuotes = MutableStateFlow<List<QuoteEntity>>(emptyList())
    val favoriteQuotes: StateFlow<List<QuoteEntity>> = _favoriteQuotes.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                repository.getFavoriteQuotes(userId).collect { quotes ->
                    _favoriteQuotes.value = quotes
                }
            }
        }
    }

    fun deleteFavorite(quote: QuoteEntity) {
        viewModelScope.launch {
            repository.deleteFavorite(quote)
        }
    }
}