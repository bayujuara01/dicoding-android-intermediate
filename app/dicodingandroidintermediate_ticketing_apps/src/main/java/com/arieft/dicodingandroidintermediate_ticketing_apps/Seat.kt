package com.arieft.dicodingandroidintermediate_ticketing_apps

data class Seat(
    val id: Int,
    var x: Float? = 0f,
    var y: Float? = 0f,
    var name: String,
    var isBooked: Boolean
)
