package cat.deim.asm01.pedalean2.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access") val access: String,
    @SerializedName("refresh") val refresh: String
)