package com.ort.quotesappgrupo6.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.ort.quotesappgrupo6.data.local.QuoteEntity
import com.ort.quotesappgrupo6.data.model.QuoteDto
import com.ort.quotesappgrupo6.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _quoteState = mutableStateOf<QuoteDto?>(null)
    val quoteState: State<QuoteDto?> = _quoteState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()

    private val apiKey = "ziXkf3MrVtqWUmJ6sT3f5yXg1ENEcd6uBEHrjLkd" 

    fun getNewQuote() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = repository.getRandomQuote(apiKey)
                if (!result.isNullOrEmpty()) {
                    _quoteState.value = result[0]
                } else {
                    _errorMessage.value = "No se pudo obtener la frase."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de red: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveToFavorites() {
        val currentQuote = _quoteState.value ?: return
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                repository.saveFavorite(
                    QuoteEntity(
                        userId = userId,
                        quote = currentQuote.quote,
                        author = currentQuote.author,
                        category = currentQuote.category
                    )
                )
                _snackbarEvent.emit("¡Guardado en favoritos! ❤️")
            } catch (e: Exception) {
                _snackbarEvent.emit("Error al guardar")
            }
        }
    }

    fun logout() {
        auth.signOut()
    }
}