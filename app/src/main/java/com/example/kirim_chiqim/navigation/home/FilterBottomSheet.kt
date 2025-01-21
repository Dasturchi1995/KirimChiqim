package com.example.kirim_chiqim.navigation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.example.kirim_chiqim.modul.FilterDataType
import com.example.kirim_chiqim.modul.MoneyType
import com.example.kirim_chiqim.navigation.MoneyEvent
import com.example.kirim_chiqim.navigation.MoneyState
import com.example.kirim_chiqim.ui.theme.MoneyColor
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    state: MoneyState,
    onDismiss: ()-> Unit,
    event: (MoneyEvent)-> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 15.dp),
                thickness = 1.dp,
                color = Color.DarkGray
            )
            Text(
                text = "Turi bo`yich",
                fontSize = 12.sp,
                color = Color.Gray
            )
            RadiButtonText(
                selected = state.filterMoneyType == MoneyType.ALL,
                onSelected = {
                    event(MoneyEvent.UpdateFilterMoneyType(MoneyType.ALL))
                },
                text = "Hammasi"
            )
            Row {
                RadiButtonText(
                    selected = state.filterMoneyType == MoneyType.INCOME,
                    onSelected = {
                        event(MoneyEvent.UpdateFilterMoneyType(MoneyType.INCOME))
                    },
                    text = "Kirim"
                )
                Spacer(Modifier.size(30.dp))
                RadiButtonText(
                    selected = state.filterMoneyType == MoneyType.EXPANSE,
                    onSelected = {
                        event(MoneyEvent.UpdateFilterMoneyType(MoneyType.EXPANSE))
                    },
                    text = "Chiqim"
                )
            }
            Spacer(Modifier.size(20.dp))
            Text(
                text = "Sana bo`yich",
                fontSize = 12.sp,
                color = Color.Gray
            )
            RadiButtonText(
                selected = state.filterDataType == FilterDataType.ALL,
                onSelected = {
                    event(MoneyEvent.UpdateFilterByDate(FilterDataType.ALL))
                },
                text = "Hammasi"
            )
            Row {
                RadiButtonText(
                    selected = state.filterDataType == FilterDataType.TODAY,
                    onSelected = {
                        event(MoneyEvent.UpdateFilterByDate(FilterDataType.TODAY))
                    },
                    text = "Bugun"
                )
                Spacer(Modifier.size(30.dp))
                RadiButtonText(
                    selected = state.filterDataType == FilterDataType.ANY,
                    onSelected = {
                        event(MoneyEvent.UpdateFilterByDate(FilterDataType.ANY))
                    },
                    text = "Ixtiyoriy sana"
                )
            }
            if (state.filterDataType == FilterDataType.ANY){
                Column {
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = "Ushbu sanadan",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    WheelDatePicker(
                        modifier = Modifier.fillMaxWidth(),
                        startDate = LocalDate.now(),
                        minDate = LocalDate.of(2000,1,1),
                        maxDate = LocalDate.now(),
                        rowCount = 3,
                        textColor = Color.Black,
                        selectorProperties = WheelPickerDefaults.selectorProperties(
                            enabled = true,
                            color = MoneyColor.copy(0.2f),
                            border = BorderStroke(2.dp, MoneyColor)
                        )
                    ){snappedDate ->
                        event(MoneyEvent.UpdateFilterStartDate(snappedDate))
                    }
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = "Ushbu sanagacha",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    WheelDatePicker(
                        modifier = Modifier.fillMaxWidth(),
                        startDate = LocalDate.now(),
                        minDate = LocalDate.of(2000,1,1),
                        maxDate = LocalDate.now(),
                        rowCount = 3,
                        textColor = Color.Black,
                        selectorProperties = WheelPickerDefaults.selectorProperties(
                            enabled = true,
                            color = MoneyColor.copy(0.2f),
                            border = BorderStroke(2.dp, MoneyColor)
                        )
                    ){snappedDate ->
                        event(MoneyEvent.UpdateFilterEndDate(snappedDate))
                    }
                }
            }

            Spacer(Modifier.size(20.dp))
            Button(
                onClick = {
                    event(MoneyEvent.Filter)
                    scope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MoneyColor
                )
            ) {
                Text(
                    text = "Filter"
                )
            }
            Spacer(Modifier.size(20.dp))
        }
    }
}

@Composable
fun RadiButtonText(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onSelected: ()-> Unit,
    text: String = ""
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = MoneyColor,
                unselectedColor = MoneyColor
            )
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Preview
@Composable
private fun Preview() {
    FilterBottomSheet(
        state = MoneyState(),
        onDismiss = {},
        event = {},
    )
}