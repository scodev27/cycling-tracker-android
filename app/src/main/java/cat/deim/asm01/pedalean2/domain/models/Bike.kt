package cat.deim.asm01.pedalean2.domain.models

import java.util.Date

data class Bike(
    val uuid: String,
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val type: String,
    val meters: Int,
    val lastUse: Date,
    val lastMaintenance: Date,
    val batteryLevel: Int,
    val isRented: Boolean,
    val isReserved: Boolean
)