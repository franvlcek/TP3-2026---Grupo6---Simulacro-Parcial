package com.ort.quotesappgrupo6.data.repository

import com.ort.quotesappgrupo6.data.local.QuoteDao
import com.ort.quotesappgrupo6.data.local.QuoteEntity
import com.ort.quotesappgrupo6.data.model.QuoteDto
import com.ort.quotesappgrupo6.data.network.QuoteApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepository @Inject constructor(
    private val apiService: QuoteApiService,
    private val quoteDao: QuoteDao
) {
    suspend fun getRandomQuote(apiKey: String): List<QuoteDto>? {
        return try {
            val response: Response<List<QuoteDto>> = apiService.getRandomQuote(apiKey)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    // Corregido: Ahora pedimos los favoritos filtrando por el ID del usuario
    fun getFavoriteQuotes(userId: String): Flow<List<QuoteEntity>> = 
        quoteDao.getFavoritesByUser(userId)

    suspend fun saveFavorite(quote: QuoteEntity) = quoteDao.insertFavorite(quote)

    suspend fun deleteFavorite(quote: QuoteEntity) = quoteDao.deleteFavorite(quote)
}