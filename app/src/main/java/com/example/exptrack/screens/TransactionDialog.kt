package com.example.exptrack.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun TransactionDialog(onDismiss:()->Unit, des:String){
    Dialog(
        onDismissRequest = onDismiss ,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)){
                Box(modifier = Modifier.fillMaxWidth()
                    .padding(top = 6.dp)
                ){
                    Text(text = "Description", fontSize = 20.sp, modifier = Modifier.align(Alignment.Center))
                }

                Spacer(modifier = Modifier.height(10.dp))
                if (des != "") {
                    Text(text = des )
                }else{
                    Text(text = "No description")
                }


            }
        }
    }
}

@Composable
@Preview
fun TransactionDialogPreview(){
    TransactionDialog(onDismiss = {  }, des = "")
}