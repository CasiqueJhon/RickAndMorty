package com.jhoncasique.rickandmorty.domain.usecase

import com.jhoncasique.rickandmorty.data.ApiService
import com.jhoncasique.rickandmorty.domain.model.Character
import com.jhoncasique.rickandmorty.domain.model.CharacterResponse
import javax.inject.Inject

class GetCharactersUseCaseImpl @Inject constructor(
    private val apiService: ApiService
) : GetCharactersUseCase {

    override suspend fun execute(page: Int): CharacterResponse {
        return apiService.getCharacters(page)
    }
}