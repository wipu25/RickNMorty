package com.example.ricknmorty.models.response

import com.google.gson.annotations.SerializedName

data class AllEpisodes(
    @SerializedName("info")
    val info: PageInfo,
    @SerializedName("results")
    val results: List<Episode>
)

data class Episode(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("characters")
    val charactersListUrl: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)