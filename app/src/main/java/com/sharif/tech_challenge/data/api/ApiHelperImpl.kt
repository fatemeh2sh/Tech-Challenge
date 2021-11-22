package com.sharif.tech_challenge.data.api

import com.sharif.tech_challenge.data.model.CardModel
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(var apiService: ApiService) : ApiHelper {

    override suspend fun allServices(): Response<CardModel> =
            apiService.allCards()

}