package com.example.ricknmorty.models.response

import android.icu.text.IDNA
import com.google.gson.annotations.SerializedName

data class AllCharacters (
    @SerializedName("info")
    val info: AllCharactersInfo,
    @SerializedName("results")
    val charactersList :List<CharacterInfo>
        )

data class AllCharactersInfo (
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
    )

