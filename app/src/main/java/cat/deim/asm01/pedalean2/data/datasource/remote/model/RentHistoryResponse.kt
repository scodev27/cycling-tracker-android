package cat.deim.asm01.pedalean2.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import cat.deim.asm01.pedalean2.common.models.RentModel

data class RentHistoryResponse(
    @SerializedName("rents") val rents: List<RentModel>
)