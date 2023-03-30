package com.jhoncasique.rickandmorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jhoncasique.rickandmorty.data.CharactersPagingSource
import com.jhoncasique.rickandmorty.domain.model.CharacterResponse
import com.jhoncasique.rickandmorty.domain.usecase.GetCharactersUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCaseImpl
) : ViewModel() {

    private val _charactersState = MutableStateFlow<CharacterResponse?>(null)
    val charactersState: StateFlow<CharacterResponse?> = _charactersState

    var currentPage = 1
    var totalPages = 42

    fun getCharacters(page: Int = 0) {
        viewModelScope.launch {
            val result = getCharactersUseCase.execute(page)
            if (page == 0) {
                _charactersState.value = result
            } else {
                val currentCharacters = _charactersState.value?.results.orEmpty().toMutableList()
                currentCharacters.addAll(result.results)
                _charactersState.value = result.copy(results = currentCharacters)
            }
        }
    }

    fun loadNextPage() {
        currentPage++
        getCharacters(currentPage)
    }

    fun loadPreviousPage() {
        currentPage--
        getCharacters(currentPage)
    }

    fun hasPreviousPage(): Boolean {
        return currentPage > 1
    }

    fun hasNextPage(): Boolean {
        return currentPage < totalPages
    }
}