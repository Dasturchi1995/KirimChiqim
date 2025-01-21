package com.example.kirim_chiqim.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.kirim_chiqim.modul.Money
import com.example.kirim_chiqim.navigation.MoneyState

@Composable
fun SummaMoney(
    modifier: Modifier = Modifier,
    state: MoneyState,
) {

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Jami:",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = state.allSum,
                    fontSize = 20.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Kirim:",
                    fontSize = 20.sp,
                    color = Color.Black,
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = state.incomeSum,
                    fontSize = 20.sp,
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )

                Text(
                    text = "Chiqim:",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(Modifier.size(5.dp))
                Text(
                    text = state.expanseSum,
                    fontSize = 20.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SummaMoney(
        state = MoneyState(),
    )
}