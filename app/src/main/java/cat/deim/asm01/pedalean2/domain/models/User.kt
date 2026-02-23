package cat.deim.asm01.pedalean2.domain.models

import java.util.Date

data class User(
    val uuid: String,
    val name: String,
    val username: String,
    val email: String,
    val courseGroup: String,
    val phoneNumber: String,
    val birthDate: Date,
    val isInRenting: Boolean,
    val totalRentingTime: Int,
    val totalRents: Int,
    val creditCardNumber: String,
    val creditCardCvv: Int,
    val creditCardExpirationDateMonth: Int,
    val creditCardExpirationDateYear: Int
)