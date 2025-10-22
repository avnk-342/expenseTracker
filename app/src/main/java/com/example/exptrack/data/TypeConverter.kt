package com.example.exptrack.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTransactionType(value: TransactionType): String{
        return  value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }

}