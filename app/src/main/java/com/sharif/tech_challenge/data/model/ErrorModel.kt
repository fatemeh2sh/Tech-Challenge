package com.sharif.tech_challenge.data.model

import com.squareup.moshi.Json

data class ErrorApiModel (
    val status: Int? = null,
    val message: String? = null,
    val data:String? = null
)

data class CheckErrorApiClass(
    var status: Int? = -1,
    @Json(name = "message")
    var message_data: String? = null,
    var data : Any?
):Throwable()


