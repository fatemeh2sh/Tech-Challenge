package com.sharif.tech_challenge.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sharif.tech_challenge.data.model.CardModel
import com.sharif.tech_challenge.repository.MainRepository
import com.sharif.tech_challenge.service.sound.Sound
import com.sharif.tech_challenge.service.vibrate.Vibrate
import com.sharif.tech_challenge.ui.base.BaseViewModel
import com.sharif.tech_challenge.utils.event.SingleLiveEvent
import com.sharif.tech_challenge.utils.networkHelper.ConnectionNet
import com.sharif.tech_challenge.utils.networkHelper.ResultNet
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        @ApplicationContext private val context: Context,
        private val mainMenuRepository: MainRepository,
        private val connectionNet: ConnectionNet,
        private val vibrate: Vibrate,
        private val sound: Sound) :
    BaseViewModel(connectionNet = connectionNet,context = context) {

    private val _cardResult = SingleLiveEvent<ResultNet<CardModel>>()
    val cardResult: LiveData<ResultNet<CardModel>>
        get() = _cardResult

    fun getCards() {
        getData(_cardResult) {
            mainMenuRepository.getAllCards()
        }
    }

    fun playSound(url:String){
        viewModelScope.launch(Dispatchers.IO) {
            sound.start(url)
        }

       // sound.start(url)
    }

    fun stopSound(){
        viewModelScope.launch {
            sound.stop()
        }
    }

    fun startVibrate(){
        vibrate.start()
    }
}