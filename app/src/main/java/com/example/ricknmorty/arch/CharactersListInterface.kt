package com.example.ricknmorty.arch

import android.text.Editable
import com.example.ricknmorty.models.response.CharacterInfo

//else we can't pass back char info to char detail page

interface CharactersListInterface {
    fun onSelected(characterInfo: CharacterInfo)

    fun updateInputFilter(filterType: CharacterFilterType, value: String)
}