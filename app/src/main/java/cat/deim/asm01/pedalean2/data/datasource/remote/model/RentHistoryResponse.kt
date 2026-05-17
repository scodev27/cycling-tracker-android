package cat.deim.asm01.pedalean2.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import com.pedalean2.common.datasource.local.model.RentModel

data class RentHistoryResponse(
    @SerializedName("rents") val rents: List<RentModel>
)