package com.sharif.tech_challenge.data.model

interface BaseResponseListModel<T> {
        val status: Int?
        val message: String ?
        val data: List<T>?
}

interface BaseResponseModel<T> {
    val status: Int?
    val message: String ?
    val data: T?
}

//data class BaseResponseImpl(
//        override val status: Int?,
//        override val message: String?,
//        override val data: Any?
//) : BaseResponseModel<Any?>