package com.sharif.tech_challenge.utils.networkHelper

import com.sharif.tech_challenge.data.model.CheckErrorApiClass

object HandleErrorNet {

    fun mapNetworkThrowable(throwable: Throwable): String? {
        return throwable.message
    }

    fun nullBodyException():String{
        return "body is null"
    }

    fun parseCustomError(errorBodyJson: String): CheckErrorApiClass? {

        if(errorBodyJson.isNotEmpty()) {
//            var errorApiModel: ErrorApiModel = errorBodyJson.toConvertStringJsonToModel(ErrorApiModel::class.java)
//
//            return CheckErrorApiClass(
//                    status = errorApiModel.status,
//                    message_data = errorApiModel.message
//             )
        }
        return null
    }
}