package cat.deim.asm01.pedalean2.common.models

import com.google.gson.annotations.SerializedName

data class RentModel(
    val uuid: String,
    @SerializedName("time_start") val timeStart: String,
    @SerializedName("time_end") val timeEnd: String?,
    @SerializedName("is_rented") val isRented: Boolean,
    @SerializedName("rent_time") val rentTime: Int,
    @SerializedName("rent_meters") val rentMeters: Int,
    @SerializedName("rent_start_lat") val rentStartLatitude: Double,
    @SerializedName("rent_start_lng") val rentStartLongitude: Double,
    val bike: BikeRentModel,
    val user: UserRentModel
)

data class BikeRentModel(val uuid: String, val name: String)

data class UserRentModel(
    val username: String,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)