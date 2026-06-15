package cat.deim.asm01.pedalean2.data.datasource.remote.model

import com.google.gson.annotations.SerializedName
import cat.deim.asm01.pedalean2.common.models.UserModel

data class UserResponse(
    @SerializedName("user") val user: UserModel
)