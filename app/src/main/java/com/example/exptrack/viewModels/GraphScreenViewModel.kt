package com.example.exptrack.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.exptrack.MyApplication
import com.example.exptrack.data.TransactionDatabase
import com.example.exptrack.data.TransactionEntities
import com.example.exptrack.data.TransactionsDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.format.DateTimeFormatter




class GraphScreenViewModel(dao: TransactionsDao): ViewModel() {
    
}




class GraphScreenViewModelFactory(application: MyApplication): ViewModelProvider.Factory{
    private val myDao: TransactionsDao = (application as MyApplication).dao
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GraphScreenViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return GraphScreenViewModel(myDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}