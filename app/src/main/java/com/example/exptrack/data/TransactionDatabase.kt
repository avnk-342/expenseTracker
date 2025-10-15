package com.example.exptrack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exptrack.utils.Contants.Companion.DATABASE_NAME

@Database(
    entities = [TransactionEntities::class],
    version = 1
)
abstract class TransactionDatabase: RoomDatabase() {

    abstract fun transactionDao(): TransactionsDao

    companion object{

        @JvmStatic
        fun getDatabase(context: Context): TransactionDatabase{
            return Room.databaseBuilder(
                context,
                TransactionDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}