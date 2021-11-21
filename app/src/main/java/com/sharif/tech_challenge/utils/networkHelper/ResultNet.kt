package com.sharif.tech_challenge.utils.networkHelper

import com.sharif.tech_challenge.data.model.CheckErrorApiClass


/**
 * State Management for UI & Data
 */
sealed class ResultNet<T> {

    class Loading<T> : ResultNet<T>()

    data class Success<T>(val data: T) : ResultNet<T>()

    data class ErrorNetwork<T>(val message: String?) : ResultNet<T>()

    data class ErrorException<T>(val message: String?) : ResultNet<T>()

    data class ErrorApi<T>(val errorCls: CheckErrorApiClass?) : ResultNet<T>()
}

