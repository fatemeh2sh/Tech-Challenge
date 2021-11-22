package com.sharif.tech_challenge.repository

import com.sharif.tech_challenge.utils.networkHelper.CallBackNet
import com.sharif.tech_challenge.ui.base.BaseRepository
import javax.inject.Inject

class MainRepository @Inject constructor() : BaseRepository(){

    suspend fun getAllCards() =
        CallBackNet.apiCall { apiHelper.allServices() }
}