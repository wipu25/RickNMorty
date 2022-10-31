package com.example.ricknmorty.network

import com.example.ricknmorty.models.response.AllCharacters
import com.example.ricknmorty.models.response.CharacterInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

class NetworkLayer {
    private var retrofit : Retrofit? = null
    private val baseUrl : String = "https://rickandmortyapi.com/"
    private var apiInterface : APIInterface? = null

    init {
        retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit!!.create(APIInterface::class.java)
    }

    suspend fun getCharacter(): Response<CharacterInfo> {
       return apiInterface!!.getCharacter()
    }

    suspend fun getAllCharacter(): Response<AllCharacters> {
        return apiInterface!!.getAllCharacter()
    }
}

interface APIInterface {
    @GET("api/character/2")
    suspend fun getCharacter() : Response<CharacterInfo>

    @GET("api/character")
    suspend fun getAllCharacter() : Response<AllCharacters>
}