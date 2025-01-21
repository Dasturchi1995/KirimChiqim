package com.example.kirim_chiqim.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kirim_chiqim.modul.Money
import com.example.kirim_chiqim.modul.MoneyType
import com.example.kirim_chiqim.ui.theme.MoneyColor
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun MoneyItem(
    money: Money,
    onMenuClick: (index: Int)->Unit
) {

    val text1 = "Kirim"
    val text2 = "Chiqim"

    var showPopupMenu by remember {
        mutableStateOf(false)
    }

    val color = if (money.type == MoneyType.INCOME){
        MoneyColor
    } else {
        Color.Red
    }
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .pointerInput(Unit){
                    detectTapGestures(
                        onPress = {
                            println("Item is pressed")
                        },
                        onLongPress = {
                            println("Item is long pressed")
                            showPopupMenu = true
                        }
                    )
                },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = money.summa.toString(),
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.size(20.dp))

                    Text(
                        text = money.title,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(color, shape = CircleShape)
                            .padding(5.dp)
                            .clip(CircleShape)
                    ) {
                        val text = if (money.type == MoneyType.INCOME){
                            text1
                        }else{
                            text2
                        }
                        Text(
                            text = text,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                    Spacer(Modifier.size(10.dp))
                    Text(
                        text = convertLongToTime(money.created_at),
                        color = Color.Black,
                        fontSize = 12.sp,
                    )
                }
            }
            Spacer(Modifier.size(5.dp))
            CustomDropdownMenu(
                menuItems = listOf("Update","Delete"),
                onDismiss = {
                    showPopupMenu = false
                },
                onMenuClick = {index ->
                    showPopupMenu = false
                    onMenuClick(index)
                },
                showMenu = showPopupMenu
            )
        }
    }
}
fun convertLongToTime(time: Long): String{
    var date = Date(time)
    var format = SimpleDateFormat("yyyy.MM.dd. HH:mm")
    return format.format(date)
}

@Preview
@Composable
private fun Preview() {
    MoneyItem(
        money = Money(),
        onMenuClick = {}
    )
}