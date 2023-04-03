package com.jhoncasique.rickandmorty.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jhoncasique.rickandmorty.R
import com.jhoncasique.rickandmorty.presentation.viewmodel.CharactersViewModel
import com.jhoncasique.rickandmorty.domain.model.Character

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersFragment() {

    val viewModel: CharactersViewModel by viewModel()
    val charactersState = viewModel.charactersState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getCharacters()
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
                                viewModel.loadNextPage()
                            }
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterItem(
    character: Character,
    navController: NavController? = null
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        onClick = { navController?.let { navigateToCharacterDetails(it, character.id) } }
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

fun navigateToCharacterDetails(navController: NavController, characterId: Int) {
    val bundle = bundleOf("characterId" to characterId)
    navController.navigate(R.id.action_charactersFragment_to_characterDetailsFragment, bundle)
}




