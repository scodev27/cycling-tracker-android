package cat.deim.asm01.pedalean2.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

fun String?.toDate(): Date {
    if (this == null) return Date()
    return try {
        dateFormatter.parse(this) ?: Date()
    } catch (e: Exception) {
        Date()
    }
}