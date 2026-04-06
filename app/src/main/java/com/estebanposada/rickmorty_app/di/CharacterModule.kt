package com.estebanposada.rickmorty_app.di

import com.estebanposada.data.repository.CharacterRepositoryImpl
import com.estebanposada.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CharacterModule {
    @Binds
    fun bindsCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository
}
