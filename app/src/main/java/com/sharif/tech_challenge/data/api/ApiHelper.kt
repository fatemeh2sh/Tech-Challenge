package com.sharif.tech_challenge.data.api

import com.sharif.tech_challenge.data.model.ServiceModel
import retrofit2.Response

interface ApiHelper {
    suspend fun allServices(): Response<ServiceModel>
}