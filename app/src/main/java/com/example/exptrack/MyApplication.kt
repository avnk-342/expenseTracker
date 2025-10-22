package com.example.exptrack

import android.app.Application
import com.example.exptrack.data.TransactionDatabase
import com.example.exptrack.data.TransactionsDao

class MyApplication: Application() {
    lateinit var database : TransactionDatabase
    lateinit var dao : TransactionsDao
    override fun onCreate() {
        super.onCreate()
        database = TransactionDatabase.getDatabase(this)
        dao = database.transactionDao()

    }
}