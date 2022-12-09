package com.example.ricknmorty.network

import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.models.response.AllEpisodes
import com.example.ricknmorty.models.response.AllLocations
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object NetworkLayer {
    private val baseUrl: String = "https://rickandmortyapi.com/api/"
    val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiInterface: APIInterface = retrofit.create(APIInterface::class.java)
    val apiClient = APIClient(apiInterface)
}

interface APIInterface {
    @GET("character")
    suspend fun getFilterCharacter(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String
    ): Response<AllCharacters>

    @GET("character")
    suspend fun getCharacterByPage(@Query("page") page: Int): Response<AllCharacters>

    @GET("location")
    suspend fun getAllLocations(@Query("page") page: Int): Response<AllLocations>

    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") page: Int): Response<AllEpisodes>
}