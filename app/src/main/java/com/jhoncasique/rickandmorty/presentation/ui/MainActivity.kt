package com.jhoncasique.rickandmorty.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jhoncasique.rickandmorty.presentation.viewmodel.CharacterDetailViewModel
import com.jhoncasique.rickandmorty.presentation.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelStore = ViewModelStore()
        setContent {
            navController = rememberNavController()
            navController.setViewModelStore(viewModelStore)
            MyapplicationTheme()
        }
    }

    @Composable
    fun CharacterListScreen() {
        val viewModel: CharactersViewModel = viewModel()
        val detailViewModel: CharacterDetailViewModel = viewModel()
                NavHost(navController = navController, startDestination = "charactersFragment") {
            composable(
                route = "charactersFragment",
                arguments = listOf(navArgument("viewModel") { defaultValue = viewModel })
            ) {
                CharactersFragment(viewModel = it.arguments?.get("viewModel") as CharactersViewModel)
            }
            composable(
                route = "characterDetailFragment/{characterId}",
                arguments = listOf(navArgument("characterId") { type = NavType.IntType })
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getInt("characterId") ?: -1
                CharacterDetailFragment(navController = navController, characterId = characterId)
            }
        }
    }

    @Composable
    fun MyapplicationTheme() {
        Scaffold {
            CharacterListScreen()
        }
    }
}