package com.jhoncasique.rickandmorty.domain.usecase

import com.jhoncasique.rickandmorty.domain.model.Character

interface GetCharacterDetailUseCase {

    suspend fun execute(id: Int): Character
}