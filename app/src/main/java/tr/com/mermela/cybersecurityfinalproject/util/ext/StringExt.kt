package tr.com.mermela.cybersecurityfinalproject.util.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toTurkishDateFormat(): String {
    // Original format matches the input string format with seconds
    val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    // Updated target format to include seconds
    val targetFormat = SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale("tr", "TR"))
    return try {
        val date: Date = originalFormat.parse(this) ?: return "Invalid Date"
        targetFormat.format(date)
    } catch (e: Exception) {
        "Invalid Date"
    }
}
