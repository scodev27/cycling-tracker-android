package cat.deim.asm01.pedalean2.domain.models

import java.util.Date

data class Rent(
    val uuid: String,
    val timeStart: Date,
    val timeEnd: Date,
    val isRented: Boolean,
    val rentTime: Int,
    val rentMeters: Int,
    val rentStartLatitude: Float,
    val rentStartLongitude: Float,
    val rents: BikeRent,
    val rented_by: UserRent
)

data class BikeRent(
    val uuid: String,
    val name: String
)

data class UserRent(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
)