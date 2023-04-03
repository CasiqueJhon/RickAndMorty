package com.jhoncasique.rickandmorty

import com.jhoncasique.rickandmorty.domain.model.CharacterResponse
import com.jhoncasique.rickandmorty.domain.model.Info
import com.jhoncasique.rickandmorty.domain.usecase.GetCharactersUseCase
import com.jhoncasique.rickandmorty.presentation.viewmodel.CharactersViewModel
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import com.jhoncasique.rickandmorty.domain.model.Character
import com.jhoncasique.rickandmorty.domain.usecase.GetCharactersUseCaseImpl
import org.junit.runner.RunWith
import org.hamcrest.core.Is.`is`
import org.hamcrest.Matchers.hasSize
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @Mock
    private lateinit var getCharactersUseCase: GetCharactersUseCaseImpl

    private lateinit var charactersViewModel: CharactersViewModel

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(getCharactersUseCase)
    }

    @Test
    fun `when getCharacters is called with page 0, then charactersState is set with the result`() = runBlocking {
        val characterResponse = mock<CharacterResponse>()
        whenever(getCharactersUseCase.execute(0)).thenReturn(characterResponse)

        charactersViewModel.getCharacters(0)

        assertThat(charactersViewModel.charactersState.value, `is`(characterResponse))
    }

    @Test
    fun `when getCharacters is called with a non-zero page, then charactersState is updated with the result`() = runBlocking {
        val characterResponse1 = CharacterResponse(
            info = Info(count = 2, pages = 2),
            results = listOf(Character(1, "Rick", "Alive", "Human", "Male", ""))
        )
        val characterResponse2 = CharacterResponse(
            info = Info(count = 2, pages = 2),
            results = listOf(Character(2, "Morty", "Alive", "Human", "Male", ""))
        )
        whenever(getCharactersUseCase.execute(0)).thenReturn(characterResponse1)
        whenever(getCharactersUseCase.execute(1)).thenReturn(characterResponse2)

        charactersViewModel.getCharacters(0)
        charactersViewModel.getCharacters(1)

        assertThat(charactersViewModel.charactersState.value?.results, hasSize(2))
        assertThat(charactersViewModel.charactersState.value?.results?.get(0)?.name, `is`("Rick"))
        assertThat(charactersViewModel.charactersState.value?.results?.get(1)?.name, `is`("Morty"))
    }

}

