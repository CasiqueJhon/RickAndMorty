package com.jhoncasique.rickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jhoncasique.rickandmorty.domain.model.Character
import com.jhoncasique.rickandmorty.domain.usecase.GetCharactersUseCaseImpl

class CharactersPagingSource(private val getCharactersUseCase: GetCharactersUseCaseImpl) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1
            val response = getCharactersUseCase.execute(page)
            val characters = response.results
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.info.next != null) page + 1 else null
            LoadResult.Page(characters, prevKey, nextKey)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

