package com.example.exptrack.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exptrack.data.TransactionDatabase
import com.example.exptrack.data.TransactionEntities
import com.example.exptrack.data.TransactionsDao

class DialogViewModel (dao: TransactionsDao): ViewModel(){
    val transactions = dao.getAllTransaction()

    fun getDescription(id:Int, list: List<TransactionEntities>): String? {
        list.forEach{
            if(it.id==id) return it.description
        }
        return null
    }
}


class DialogViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DialogViewModel::class.java)){
            val dao = TransactionDatabase.getDatabase(context).transactionDao()
            @Suppress("UNCHECKED_CAST")
            return DialogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}