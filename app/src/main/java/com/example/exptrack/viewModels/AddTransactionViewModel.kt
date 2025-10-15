package com.example.exptrack.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exptrack.data.TransactionDatabase
import com.example.exptrack.data.TransactionEntities
import com.example.exptrack.data.TransactionsDao

@Suppress("UNREACHABLE_CODE")
class AddTransactionViewModel(private val dao: TransactionsDao): ViewModel() {
    suspend fun addTransaction(transactionEntities: TransactionEntities): Boolean{
        return try {
            dao.upsertTransaction(transactionEntities)
            return true
        }catch (ex: Throwable){
            return false
        }
    }
}

class AddExpenseViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddTransactionViewModel::class.java)){
            val dao = TransactionDatabase.getDatabase(context).transactionDao()
            @Suppress("UNCHECKED_CAST")
            return AddTransactionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}