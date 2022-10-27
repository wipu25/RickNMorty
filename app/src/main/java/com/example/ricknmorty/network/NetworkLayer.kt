package com.example.ricknmorty.network

import com.example.ricknmorty.models.response.CharacterInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

class NetworkLayer {
    private var retrofit : Retrofit? = null
    private val baseUrl : String = "https://rickandmortyapi.com/"

    init {
        retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun getCharacter(): Response<CharacterInfo> {
       val apiInterface = retrofit!!.create(APIInterface::class.java)
       return apiInterface.getCharacter()
    }
}

interface APIInterface {
    @GET("api/character/2")
    suspend fun getCharacter() : Response<CharacterInfo>
}