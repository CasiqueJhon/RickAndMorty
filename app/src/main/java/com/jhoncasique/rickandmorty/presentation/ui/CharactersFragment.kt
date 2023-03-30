package com.jhoncasique.rickandmorty.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.jhoncasique.rickandmorty.presentation.viewmodel.CharactersViewModel
import com.jhoncasique.rickandmorty.domain.model.Character

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersFragment(charactersViewModel: CharactersViewModel) {
    val charactersState = charactersViewModel.charactersState.collectAsState()

    LaunchedEffect(charactersViewModel) {
        charactersViewModel.getCharacters()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val characters = charactersState.value?.results) {
                null -> {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
                else -> {
                    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                        itemsIndexed(characters) { index, character ->
                            CharacterItem(character = character)
                            if (index == characters.lastIndex && characters.size < (charactersState.value?.info?.count
                                    ?: 0)
                            ) {
                                charactersViewModel.loadNextPage()
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CharacterItem(character: Character) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(character.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clip(MaterialTheme.shapes.medium)
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = character.species,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}




