package com.sharif.tech_challenge.data.api

import com.sharif.tech_challenge.data.model.CardModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("master/tempelate.json")
    suspend fun allCards(): Response<CardModel>


}