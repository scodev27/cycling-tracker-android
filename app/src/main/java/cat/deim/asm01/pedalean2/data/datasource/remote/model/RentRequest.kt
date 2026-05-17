package cat.deim.asm01.pedalean2.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class RentRequest(
    @SerializedName("bike_uuid") val bikeUuid: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)