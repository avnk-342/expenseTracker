package com.example.exptrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exptrack.utils.Contants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class TransactionEntities(

    val title: String,
    val date: String,
    val amount: Double,
    val type: String, // weather it is INCOME or EXPENSE
    val description: String?, //if user wants to add description

    @PrimaryKey(autoGenerate = true)
    val id: Int=0,

)
