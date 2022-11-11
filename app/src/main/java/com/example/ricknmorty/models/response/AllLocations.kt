package com.example.ricknmorty.models.response

data class AllLocations (
    val info: PageInfo,
    val result: List<Location>
)

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)

