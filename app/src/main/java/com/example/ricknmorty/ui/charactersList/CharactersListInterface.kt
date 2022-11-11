package com.example.ricknmorty.ui.charactersList

import com.example.ricknmorty.models.response.CharacterInfo

//else we can't pass back char info to char detail page

interface CharactersListInterface {
    fun onSelected(characterInfo: CharacterInfo)
}