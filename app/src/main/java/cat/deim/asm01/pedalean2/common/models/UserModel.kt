package cat.deim.asm01.pedalean2.common.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    val uuid: String,
    val name: String,
    @SerializedName("username") val userName: String,
    val email: String,
    @SerializedName("course_group") val courseGroup: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("is_in_renting") val isInRenting: Boolean,
    @SerializedName("total_renting_time") val totalRentingTime: Int,
    @SerializedName("total_rents") val totalRents: Int,
    @SerializedName("credit_card_number") val creditCardNumber: String,
    @SerializedName("credit_card_cvv") val creditCardCvv: Int,
    @SerializedName("credit_card_expiration_date_month") val creditCardExpirationDateMonth: Int,
    @SerializedName("credit_card_expiration_date_year") val creditCardExpirationDateYear: Int
)