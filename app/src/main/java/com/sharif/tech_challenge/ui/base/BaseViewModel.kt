package com.sharif.tech_challenge.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharif.tech_challenge.R
import com.sharif.tech_challenge.utils.event.SingleLiveEvent
import com.sharif.tech_challenge.utils.getStringResources
import com.sharif.tech_challenge.utils.networkHelper.ConnectionNet
import com.sharif.tech_challenge.utils.networkHelper.ResultNet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val baseRepository: BaseRepository? = null,
    private val connectionNet: ConnectionNet? = null,
    private val context: Context? = null
):ViewModel() {

    fun <T> getData(liveData: SingleLiveEvent<ResultNet<T>>, getDataFun: suspend () -> ResultNet<T>) {
        try {
            if (liveData.value != null && liveData.value is ResultNet.Loading)
                return
            liveData.postValue(ResultNet.Loading())
            viewModelScope.launch(Dispatchers.IO) {
                if (connectionNet?.isNetworkConnected()!!) {
                    liveData.postValue(getDataFun())
                } else {
                    liveData.postValue(ResultNet.ErrorNetwork(internetException()))
                }
            }
        } catch (e: Exception) {
            liveData.postValue(ResultNet.ErrorException(e.message))
        }
    }

    private fun internetException():String{
        return getStringResources(
            context = context!!,
            R.string.msg_network_error
        )
    }
}