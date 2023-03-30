package com.jhoncasique.rickandmorty.domain.usecase

import com.jhoncasique.rickandmorty.domain.model.CharacterResponse

interface GetCharactersUseCase {
    suspend fun execute(page: Int): CharacterResponse
}