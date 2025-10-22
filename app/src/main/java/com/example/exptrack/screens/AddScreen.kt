package com.example.exptrack.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.exptrack.data.TransactionEntities
import com.example.exptrack.data.TransactionType
import com.example.exptrack.ui.theme.shape.CurvedTopAppBar
import com.example.exptrack.utils.DateFormat
import com.example.exptrack.viewModels.AddExpenseViewModelFactory
import com.example.exptrack.viewModels.AddTransactionViewModel
import kotlinx.coroutines.launch


@Composable
fun addScreen(navController: NavController){
    Scaffold{ innerPadding ->

        val viewModel = AddExpenseViewModelFactory(LocalContext.current).create(AddTransactionViewModel::class.java)
        val coroutineScope = rememberCoroutineScope()
        val scrollState = rememberScrollState()

        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfffcfcfc))
            .padding(innerPadding)
            .verticalScroll(scrollState)
        ){
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(277.dp)
                    .align(Alignment.TopCenter),
                shape = CurvedTopAppBar(0.2f),
                color = Color(0xff358882),
            ) {}

            Column (
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,

            ) {
                Spacer(Modifier.height(40.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "back",
                            tint = Color.White
                        )
                    }
                    Text(
                        "Add Expense",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.MoreHoriz,
                            contentDescription = "options",
                            tint = Color.White
                        )
                    }
                }
                Spacer(Modifier.height(40.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    dataForm(onAddClick = {
                        coroutineScope.launch {
                            if (viewModel.addTransaction(it)) {
                                navController.popBackStack()
                            }
                        }
                    })
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}



@Composable
fun dataForm(onAddClick: (model: TransactionEntities)->Unit){

    val amount = remember { mutableStateOf("") }
    val title = remember { mutableStateOf("") }
    val date = remember { mutableLongStateOf(0L) }
    val dateDialogVisibility = remember { mutableStateOf(false) }
    val type = remember { mutableStateOf(TransactionType.EXPENSE) }
    val description = remember { mutableStateOf("") }



    Box(modifier = Modifier
        .shadow(10.dp, shape = RoundedCornerShape(20.dp))
        .clip(RoundedCornerShape(20.dp))
        .background(Color(0xFFFFFFFF))
        .padding(20.dp)
    ){
        Column {
            //amount Double
            Text(text = "AMOUNT", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value=amount.value ,
                onValueChange = { if(it.isDigitsOnly()) amount.value = it },
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color(0xff438883), unfocusedTextColor = Color.Gray)
            )
            Spacer(modifier = Modifier.height(18.dp))

            //title string
            Text(text = "TITLE", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField( value=title.value ,onValueChange = { title.value = it },
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.Black)
            )
            Spacer(modifier = Modifier.height(18.dp))

            //Date Menu string
            Text(text = "DATE", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = if(date.longValue == 0L) "" else DateFormat.formatDataToHumanReadableForm(date.longValue),
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { dateDialogVisibility.value = true },
                false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Gray,
                    disabledTextColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp),
            )
            Spacer(modifier = Modifier.height(18.dp))


            //Type dropdown menu string
            Text(text = "TYPE", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            typeDropdown(selectedType = type.value, onTypeSelected = { type.value = it })

            Spacer(modifier = Modifier.height(18.dp))

            //description string
            Text(text = "DESCRIPTION", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField( value=description.value ,onValueChange = { description.value = it },
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.Black)
            )
            Spacer(modifier = Modifier.height(18.dp))

            //submit button
            Button(
                onClick = {
                    val model = TransactionEntities(
                       title = title.value,
                        amount = amount.value.toDouble(),
                        date = DateFormat.formatDataToHumanReadableForm(date.longValue),
                        description = description.value,
                        type = type.value
                    )
                    onAddClick(model)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff348680)
                )
            ) {
                Text(
                    text = "Add",
                    fontSize = 22.sp
                )
            }
        }
    }
    if (dateDialogVisibility.value) {
        DatePicker(
            onDateSelected = {
                date.longValue = it
                dateDialogVisibility.value = false
            },
            onDismiss = {
                dateDialogVisibility.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(onDateSelected: (date:Long)->Unit, onDismiss: ()->Unit){
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) { Text(text = "Confirm") }
        },
        dismissButton = { TextButton(onClick = { onDateSelected(selectedDate) }) { Text(text = "Cancel") }
        }
    ) {
        androidx.compose.material3.DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun typeDropdown(selectedType: TransactionType, onTypeSelected: (TransactionType)->Unit){
    val expanded = remember {
        mutableStateOf(false)
    }

    val options = TransactionType.values()

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it},
    ) {
        TextField(
            value = selectedType.name,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .border(BorderStroke(1.dp, color = Color.Gray), shape = RoundedCornerShape(8.dp)),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
        )
        
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = { /*TODO*/ }) {
            options.forEach{ option ->
                DropdownMenuItem(
                    text = { Text(text = option.name, fontSize = 18.sp) },
                    onClick = {
                        onTypeSelected(option)
                        expanded.value = false
                    }
                )
            }
        }
    }
}



@Composable
@Preview(showBackground = true)
fun addScreenPreview(){
    addScreen(rememberNavController())
}