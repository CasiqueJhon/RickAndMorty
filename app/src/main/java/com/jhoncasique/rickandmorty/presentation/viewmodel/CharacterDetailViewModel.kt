package com.jhoncasique.rickandmorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhoncasique.rickandmorty.domain.usecase.GetCharacterDetailUseCaseImpl
import com.jhoncasique.rickandmorty.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCaseImpl
): ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character

    fun getCharacterDetails(characterId: Int) {
        viewModelScope.launch {
            val result = getCharacterDetailUseCase.execute(characterId)
            _character.value = result
        }
    }
}