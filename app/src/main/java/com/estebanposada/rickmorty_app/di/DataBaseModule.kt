package com.estebanposada.rickmorty_app.di

import android.content.Context
import androidx.room.Room
import com.estebanposada.data.models.local.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java, "r&m_characters_db"
    ).build()

    @Provides
    @Singleton
    fun provideCharacterDao(db: AppDataBase) = db.characterDao()
}
