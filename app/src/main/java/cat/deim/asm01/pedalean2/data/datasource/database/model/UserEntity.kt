package cat.deim.asm01.pedalean2.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val username: String,
    val email: String,
    val courseGroup: String,
    val phoneNumber: String,
    val birthDate: String,
    val isInRenting: Boolean,
    val totalRentingTime: Int,
    val totalRents: Int,
    val creditCardNumber: String,
    val creditCardCvv: Int,
    val creditCardExpirationDateMonth: Int,
    val creditCardExpirationDateYear: Int
)