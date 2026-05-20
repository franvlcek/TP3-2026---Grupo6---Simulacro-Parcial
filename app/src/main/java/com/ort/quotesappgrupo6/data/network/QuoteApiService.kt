package com.ort.quotesappgrupo6.data.network

import com.ort.quotesappgrupo6.data.model.QuoteDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuoteApiService {
    @GET("v1/quotes")
    suspend fun getRandomQuote(
        @Header("X-Api-Key") apiKey: String,
        @Query("category") category: String? = null
    ): Response<List<QuoteDto>>
}