package com.example.ricknmorty.network

import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.models.response.CharacterInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

object NetworkLayer {
    private val baseUrl : String = "https://rickandmortyapi.com/"
    val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val apiInterface: APIInterface = retrofit.create(APIInterface::class.java)
    val apiClient = APIClient(apiInterface)
}

interface APIInterface {
    @GET("api/character/2")
    suspend fun getCharacter() : Response<CharacterInfo>

    @GET("api/character")
    suspend fun getCharacterByPage(@Query("page") page: Int) : Response<AllCharacters>
}