package cat.deim.asm01.pedalean2.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import com.pedalean2.common.datasource.local.model.UserModel

data class UserResponse(
    @SerializedName("user") val user: UserModel
)