package com.example.oving3.model

import java.text.SimpleDateFormat
import java.util.*

data class Friend(
    var name: String,
    var birthDate: String
) {
    // Funksjon for å validere at navnet ikke er tomt
    fun isValidName(): Boolean {
        return name.isNotBlank()
    }

    // Funksjon for å validere fødselsdatoformatet (dd.MM.yyyy)
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

    // Funksjon for å formatere fødselsdato til et lesbart format
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
