package com.example.kirim_chiqim.navigation.home

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.kirim_chiqim.ui.theme.MoneyColor

@Composable
fun RadioWithText(
    modifier: Modifier = Modifier,
    select:Boolean,
    onSelected:() ->Unit,
    text:String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = select,
            onClick = {
                onSelected()
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = MoneyColor,
                unselectedColor = MoneyColor
            )
        )
        Text(
            text = text,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
private fun Pre() {
    RadioWithText(
        select = true,
        onSelected = {},
        text = "Kirim"
    )
}