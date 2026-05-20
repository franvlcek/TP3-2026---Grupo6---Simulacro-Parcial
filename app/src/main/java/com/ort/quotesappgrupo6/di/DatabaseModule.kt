package com.ort.quotesappgrupo6.di

import android.content.Context
import androidx.room.Room
import com.ort.quotesappgrupo6.data.local.QuoteDao
import com.ort.quotesappgrupo6.data.local.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuoteDatabase {
        return Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            "quotes_db"
        )
        .fallbackToDestructiveMigration() // Esto limpia la BD vieja para que no se mezclen los favoritos al cambiar de usuario
        .build()
    }

    @Provides
    fun provideQuoteDao(database: QuoteDatabase): QuoteDao {
        return database.quoteDao()
    }
}