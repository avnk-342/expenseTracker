package com.example.exptrack.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exptrack.data.TransactionDatabase
import com.example.exptrack.data.TransactionEntities
import com.example.exptrack.data.TransactionsDao

class HomeScreenViewModel(dao: TransactionsDao): ViewModel() {
    val transactions = dao.getAllTransaction()
    var dialogState by mutableStateOf(false)

    fun totalRemainingAmount(list: List<TransactionEntities>): List<String>{
        var remaining = 0.0
        var income = 0.0
        var expense = 0.0
        val emptyList: MutableList<String> = mutableListOf()
        list.forEach {
            if(it.type == "Expense"){
                remaining -= it.amount
                expense += it.amount
            }
            else if(it.type == "Income"){
                remaining += it.amount
                income += it.amount
            }
        }
        emptyList.add("\u20B9 $remaining")
        emptyList.add("\u20B9 $income")
        emptyList.add("\u20B9 $expense")
        return emptyList
    }


    fun onTransactionClick(){
        dialogState = true
    }
    fun onDismissDialog(){
        dialogState = false
    }
}


class HomeScreenViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeScreenViewModel::class.java)){
            val dao = TransactionDatabase.getDatabase(context).transactionDao()
            @Suppress("UNCHECKED_CAST")
            return HomeScreenViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}