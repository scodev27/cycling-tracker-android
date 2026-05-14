package cat.deim.asm01.pedalean2.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bikes")
data class BikeEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val type: String,
    val meters: Int,
    val lastUse: String,
    val lastMaintenance: String,
    val batteryLevel: Int,
    val isRented: Boolean,
    val isReserved: Boolean
)