package com.jhoncasique.rickandmorty.domain.model

import android.location.Location
import android.webkit.WebStorage

data class CharacterResponse(
    val info: Info,
    val results: List<Character> = emptyList()
)

data class Info(
    val count: Int,
    val pages: Int,
)

data class Character(
    val id: Int,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val image: String = "",
    val episode: List<String> = emptyList(),
    val url: String = "",
    val created: String = ""
)