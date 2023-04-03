package com.jhoncasique.rickandmorty.domain.usecase

import com.jhoncasique.rickandmorty.data.ApiService
import com.jhoncasique.rickandmorty.domain.model.Character
import javax.inject.Inject

class GetCharacterDetailUseCaseImpl @Inject constructor(
    private val apiService: ApiService
) : GetCharacterDetailUseCase {

    override suspend fun execute(id: Int): Character {
        return apiService.getCharacterById(id)
    }
}