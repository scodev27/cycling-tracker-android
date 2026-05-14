package cat.deim.asm01.pedalean2.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rents")
data class RentEntity(
    @PrimaryKey val uuid: String,
    val timeStart: String,
    val timeEnd: String,
    val isRented: Boolean,
    val rentTime: Int,
    val rentMeters: Int,
    val rentStartLatitude: Double,
    val rentStartLongitude: Double,
    val bikeUuid: String,
    val bikeName: String,
    val userUsername: String,
    val userEmail: String,
    val userFirstName: String,
    val userLastName: String
)