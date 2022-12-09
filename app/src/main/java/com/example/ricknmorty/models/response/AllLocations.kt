package com.example.ricknmorty.models.response

import com.google.gson.annotations.SerializedName

data class AllLocations(
    @SerializedName("info")
    val info: PageInfo,
    @SerializedName("results")
    val results: List<Location>
)

data class Location(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("residents")
    val residents: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
)

