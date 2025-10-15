package com.example.exptrack.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AlignVerticalBottom
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.exptrack.data.TransactionEntities
import com.example.exptrack.ui.theme.shape.CurvedTopAppBar
import com.example.exptrack.viewModels.HomeScreenViewModel
import com.example.exptrack.viewModels.HomeScreenViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier, navController: NavController){

    Scaffold(
        topBar = {},
        bottomBar = {
            Surface(
                color = Color.White,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton({ navController.navigate(navController.graph.startDestinationId)}) {
                            Icon(Icons.Filled.Home, "Home button")
                        }
                        IconButton({}) {
                            Icon(Icons.Filled.AlignVerticalBottom, "Charts")
                        }
                        IconButton({}) {}

                        IconButton({}) {
                            Icon(Icons.Filled.Wallet, "Wallet")
                        }
                        IconButton({}) {
                            Icon(Icons.Filled.AccountCircle, "Account")
                        }
                    }
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("/addScreen") },
                shape = CircleShape,
                contentColor = Color.White,
                containerColor = Color("#438883".toColorInt()),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                modifier = Modifier.offset(y = 45.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier.fillMaxSize(),
    ) {innerPadding ->

        //initiating the view model
        val viewmodel = HomeScreenViewModelFactory(LocalContext.current).create(HomeScreenViewModel::class.java)

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ){
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(277.dp)
                    .align(Alignment.TopCenter),
                shape = CurvedTopAppBar(0.2f),
                color = Color(0xff358882),
            ) {}

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                //using the viewmodel functions to get variables
                //get list of all transactions
                val state = viewmodel.transactions.collectAsState(initial = emptyList())
                //the that list to get income and expense
                val summaryCardList = viewmodel.totalRemainingAmount(state.value)
                val remainingAmount = summaryCardList[0]
                val income = summaryCardList[1]
                val expense = summaryCardList[2]

                Row(
                    Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp, top = 45.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Greet Placeholder,", color = Color.White)
                        Text("User place Holder", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    IconButton(onClick = {}){
                        Icon(Icons.Filled.Notifications, "Notifications", tint = Color.White)
                    }
                }
                Spacer(Modifier.height(15.dp))
                SummaryCard(remainingAmount, income, expense )
                PreviousTransactions(list = state.value)
            }
        }
    }
}



@Composable
fun SummaryCard(remaining: String, income: String, expense: String ){
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(color = Color(0xff2f7e79))
        .height(180.dp)
        .padding(25.dp)
    ){
        Box(
            modifier = Modifier.height(50.dp)
        ){
            Text(
                text = "Total Balance",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                text = remaining,
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(Icons.Filled.ArrowDownward,
                        contentDescription = "",
                        Modifier.clip(CircleShape).background(Color(0x4DFFFFFF)).size(19.dp),
                        tint = Color.White,
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("Income", color = Color(0xFFd0e5e4), fontSize = 18.sp)
                }

                Text(income, fontSize = 16.sp, color = Color.White)
            }
            Column {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(Icons.Filled.ArrowUpward,
                        contentDescription = "",
                        Modifier.clip(CircleShape).background(Color(0x33FFFFFF)).size(19.dp),
                        tint = Color.White,
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("Expense", color = Color(0xFFd0e5e4), fontSize = 18.sp)
                }

                Text(expense, fontSize = 16.sp, color = Color.White)
            }
        }

    }
}

@Composable
fun PreviousTransactions(list: List<TransactionEntities>){
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 3.dp)

        ){
            Text(
                text = "Transactions History",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text("See All", fontSize =  16.sp, fontWeight = FontWeight.Light)
        }

        LazyColumn(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        ){
            items(list) { item->
                item.description?.let {
                    TransactionCard(
                        des = it,
                        title = item.title,
                        amount = item.amount.toString(),
                        date = item.date,
                        color = if(item.type=="Income") Color("#148F6C".toColorInt()) else Color.Red,
                        modifier = Modifier.padding(top=8.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionCard(modifier: Modifier, title:String, amount:String, date:String, color: Color, des:String){

    val showDialog = remember {
        mutableStateOf(false)
    }

    Box (modifier = modifier
        .fillMaxWidth()
        .combinedClickable(
            onClick = {
                showDialog.value = true
            },
            onLongClick = { /* show dialog to delete the entry */ }
        )
    ){
        Column {
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = date,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
        Text(
            text = if(color == Color.Red) "- \u20B9 $amount" else "+ \u20B9 $amount",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = color
        )
    }
    if(showDialog.value){
        TransactionDialog(
            onDismiss = { showDialog.value = false },
            des
        )
    }
}



@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    HomeScreen(modifier = Modifier, rememberNavController())
//    TransactionCard(modifier = Modifier, "Vertigo", "2000", "12/12/2012", Color.Green, "")
}