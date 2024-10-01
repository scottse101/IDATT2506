package com.example.oving3.model

import java.text.SimpleDateFormat
import java.util.*

data class Friend(
    var name: String,
    var birthDate: String
) {

    fun isValidName(): Boolean {
        return name.isNotBlank()
    }

    fun isValidBirthDate(): Boolean {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(birthDate)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun formattedBirthDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = dateFormat.parse(birthDate)
        return if (date != null) {
            val formatted = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            formatted.format(date)
        } else {
            "Invalid date"
        }
    }
}
