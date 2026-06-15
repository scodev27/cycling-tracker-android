package cat.deim.asm01.pedalean2.common.models

import com.google.gson.annotations.SerializedName

data class BikeModel(
    val id: String,
    val uuid: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val type: String,
    val meters: Int,
    @SerializedName("last_use") val lastUse: String?,
    @SerializedName("last_maintenance") val lastMaintenance: String?,
    @SerializedName("battery_level") val batteryLevel: Int,
    @SerializedName("is_rented") val isRented: Boolean,
    @SerializedName("is_reserved") val isReserved: Boolean
)