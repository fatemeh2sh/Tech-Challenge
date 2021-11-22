package com.sharif.tech_challenge.utils.networkHelper

object HandleErrorNet {

    fun mapNetworkThrowable(throwable: Throwable): String? {
        return throwable.message
    }

    fun nullBodyException():String{
        return "body is null"
    }
}