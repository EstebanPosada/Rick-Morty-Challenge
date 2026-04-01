package com.estebanposada.rickmorty_app.di

import com.estebanposada.domain.repository.CharacterRepository
import com.estebanposada.domain.usecase.GetCharacterDetailUseCase
import com.estebanposada.domain.usecase.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetCharactersUseCase(repository: CharacterRepository) = GetCharactersUseCase(repository)

    @Provides
    fun provideGetCharacterDetailUseCase(repository: CharacterRepository)= GetCharacterDetailUseCase(repository)
}
