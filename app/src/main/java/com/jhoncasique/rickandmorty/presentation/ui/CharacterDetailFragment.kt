package com.jhoncasique.rickandmorty.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.jhoncasique.rickandmorty.presentation.viewmodel.CharacterDetailViewModel

@Composable
fun CharacterDetailFragment(
    navController: NavController,
    characterId: Int
) {

    val viewModel: CharacterDetailViewModel by viewModel()
    val character = viewModel.character.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.getCharacterDetails(characterId)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Character Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (val characterData = character.value) {
                null -> {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
                else -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(characterData.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = characterData.name, style = MaterialTheme.typography.h4)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Status: ${characterData.status}", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Species: ${characterData.species}", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Gender: ${characterData.gender}", style = MaterialTheme.typography.subtitle1)
                    }
                }
            }
        }
    }
}

