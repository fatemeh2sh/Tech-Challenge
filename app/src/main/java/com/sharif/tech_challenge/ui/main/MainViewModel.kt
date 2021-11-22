package com.sharif.tech_challenge.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import com.sharif.tech_challenge.data.model.CardModel
import com.sharif.tech_challenge.repository.MainRepository
import com.sharif.tech_challenge.ui.base.BaseViewModel
import com.sharif.tech_challenge.utils.event.SingleLiveEvent
import com.sharif.tech_challenge.utils.networkHelper.ConnectionNet
import com.sharif.tech_challenge.utils.networkHelper.ResultNet
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val mainMenuRepository: MainRepository,
        private val connectionNet: ConnectionNet,
        @ApplicationContext private val context: Context
    ) :BaseViewModel(connectionNet = connectionNet,context = context) {


    private val _cardResult = SingleLiveEvent<ResultNet<CardModel>>()
    val cardResult: LiveData<ResultNet<CardModel>>
        get() = _cardResult

    fun getCards() {
        getData(_cardResult)
        {
            mainMenuRepository.getAllCards()
        }
    }
}