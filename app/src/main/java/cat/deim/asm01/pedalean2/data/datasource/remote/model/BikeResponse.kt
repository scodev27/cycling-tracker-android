package cat.deim.asm01.pedalean2.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import com.pedalean2.common.datasource.local.model.BikeModel

data class BikeResponse(
    @SerializedName("bike") val bike: List<BikeModel>
)