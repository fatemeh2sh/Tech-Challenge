package com.sharif.tech_challenge.data.api

import com.sharif.tech_challenge.data.model.ServiceModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("master/tempelate.json")
    suspend fun allCards(): Response<ServiceModel>


}