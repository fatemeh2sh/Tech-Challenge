package com.sharif.tech_challenge.data.model

//Service
data class ServiceModel (
        override val status: Int,
        override val message: String,
        override val data: List<ServiceResult>
) : BaseResponseListModel<ServiceResult>

//Room
data class RoomModel (
        override val status: Int,
        override val message: String,
        override val data: List<RoomResult>
): BaseResponseListModel<RoomResult>

