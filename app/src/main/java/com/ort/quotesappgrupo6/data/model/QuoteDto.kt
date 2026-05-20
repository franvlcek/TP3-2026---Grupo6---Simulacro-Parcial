package com.ort.quotesappgrupo6.data.model

import com.google.gson.annotations.SerializedName

data class QuoteDto(
    @SerializedName("quote") val quote: String,
    @SerializedName("author") val author: String,
    @SerializedName("category") val category: String
)