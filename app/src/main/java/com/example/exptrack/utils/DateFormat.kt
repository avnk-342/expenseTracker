package com.example.exptrack.utils

import android.icu.text.SimpleDateFormat
import java.util.Locale

object DateFormat {
    fun formatDataToHumanReadableForm(dateInMillis: Long): String {
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }
}