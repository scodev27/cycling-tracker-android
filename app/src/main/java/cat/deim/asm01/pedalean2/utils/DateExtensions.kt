package cat.deim.asm01.pedalean2.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

fun String?.toDate(): Date {
    if (this == null) return Date()
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        format.parse(this) ?: Date()
    } catch (e: Exception) {
        try {
            val format2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            format2.parse(this) ?: Date()
        } catch (e2: Exception) {
            Date()
        }
    }
}