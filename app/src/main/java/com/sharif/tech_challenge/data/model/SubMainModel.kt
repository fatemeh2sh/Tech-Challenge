package com.sharif.tech_challenge.data.model

import com.squareup.moshi.Json

//region Service
data class ServiceResult (
        val id: Long,
        val name: String,
        @Json(name = "service_key")
        val serviceKey: String,
        @Json(name = "service_status")
        val serviceStatus: Long,
        @Json(name = "created_at")
        val createdAt: String,
        @Json(name = "updated_at")
        val updatedAt: String
)
//endregion

//region room
data class RoomResult (
        val id: Long,
        val number: Long,
        @Json(name = "room_status")
        val roomStatus: Long,
        @Json(name = "room_reserve")
        val roomReserve: Long,
        @Json(name = "created_at")
        val createdAt: String,
        @Json(name = "updated_at")
        val updatedAt: String,
        @Json(name = "reserve_room")
        val reserveRoom: ReserveRoomClass
)

data class ReserveRoomClass (
        val id: Long,
        @Json(name = "room_id")
        val roomID: Long,
        @Json(name = "full_name")
        val fullName: String,
        val mobile: String,
        @Json(name = "created_at")
        val createdAt: String,
        @Json(name = "updated_at")
        val updatedAt: String
)
//endregion


