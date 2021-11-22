package com.sharif.tech_challenge.data.model


data class CheckErrorApiClass(
    var status: Int? = -1,
    var message_data: String? = null,
    var data : Any?
):Throwable()


