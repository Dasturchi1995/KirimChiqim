package com.example.kirim_chiqim.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kirim_chiqim.ui.theme.MoneyColor

@Composable
fun SearchMoney(
    modifier: Modifier = Modifier,
    onValueChange: (String)-> Unit,
    onPressFilter: ()-> Unit,
    onClear: ()-> Unit
) {

    var valueChange by remember {
        mutableStateOf("")
    }

    LaunchedEffect(valueChange) {
        onValueChange.invoke(valueChange)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(MoneyColor)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = modifier.weight(1f),
            value = valueChange,
            onValueChange = {valueChange = it},
            placeholder = {
                Row {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                    Spacer(Modifier.size(5.dp))
                    Text(
                        text = "Qidirish",
                        fontSize = 16.sp
                    )
                }
            },
            trailingIcon = {
                if (valueChange.isNotBlank()){
                    IconButton(
                        onClick = {onClear.invoke()},
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = ""
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            )
        )
        IconButton(
            onClick = {
                onPressFilter.invoke()
            }
        ) {
            Icon(
                imageVector = Icons.Default.FilterAlt,
                contentDescription = "Filter"
            )
        }
    }
}

@Preview
@Composable
private fun MoneySearchPreview() {
    SearchMoney(
        onValueChange = {},
        onPressFilter = {},
        onClear = {}
    )
}