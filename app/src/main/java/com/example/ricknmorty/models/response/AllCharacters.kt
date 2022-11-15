package com.example.ricknmorty.models.response

import android.icu.text.IDNA
import com.google.gson.annotations.SerializedName

data class AllCharacters (
    @SerializedName("info")
    val info: PageInfo,
    @SerializedName("results")
    val charactersList :List<CharacterInfo>
        )

