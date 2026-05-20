package com.ort.quotesappgrupo6.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes WHERE userId = :userId")
    fun getFavoritesByUser(userId: String): Flow<List<QuoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(quote: QuoteEntity)

    @Delete
    suspend fun deleteFavorite(quote: QuoteEntity)
}