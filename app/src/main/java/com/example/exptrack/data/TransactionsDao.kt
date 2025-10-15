package com.example.exptrack.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Upsert
    suspend fun upsertTransaction(transactionEntities: TransactionEntities)

    @Delete
    suspend fun deleteTransaction(transactionEntities: TransactionEntities)

    @Query("SELECT * FROM user_exp")
    fun getAllTransaction(): Flow<List<TransactionEntities>>

    @Query("SELECT * FROM user_exp WHERE id= :Id")
    fun getDataOnId(Id:Int): List<TransactionEntities>

    @Query("SELECT * FROM user_exp WHERE type = 'income'")
    fun getIncomeValue() : List<TransactionEntities>
}