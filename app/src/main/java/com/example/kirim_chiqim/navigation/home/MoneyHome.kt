@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kirim_chiqim.navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kirim_chiqim.modul.MoneyType
import com.example.kirim_chiqim.navigation.MoneyEvent
import com.example.kirim_chiqim.navigation.MoneyState
import com.example.kirim_chiqim.navigation.component.MoneyItem
import com.example.kirim_chiqim.navigation.component.SearchMoney
import com.example.kirim_chiqim.navigation.component.SummaMoney
import com.example.kirim_chiqim.ui.theme.MoneyColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneyHome(
    navController: NavController,
    homeState: MoneyState,
    homeEvent: (MoneyEvent)-> Unit
) {

    val sheetState = rememberModalBottomSheetState()

    var isSheetState by rememberSaveable {
        mutableStateOf(false)
    }
    var showFilterSheet by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        homeEvent(MoneyEvent.GetAllMoney)
    }

    LaunchedEffect(true) {
        homeEvent(MoneyEvent.AllSumma())
    }

    if (showFilterSheet){
        FilterBottomSheet(
            onDismiss = {showFilterSheet = false},
            state = homeState,
            event = homeEvent
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    homeEvent(MoneyEvent.ClearMoney)
                    isSheetState = true
                },
                containerColor = MoneyColor
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                )
            }
        }
    ) {innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(0.8f))
                .padding(innerPadding)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchMoney(
                    onClear = {
                        homeEvent(MoneyEvent.ClearMoney)
                    },
                    onValueChange = {query->
                        homeEvent(MoneyEvent.SearchMoney(query))
                    },
                    onPressFilter = {
                        showFilterSheet = true
                    }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    SummaMoney(
                        state = homeState
                    )
                }
                items(homeState.moneyList){ money->
                    MoneyItem(
                        money = money,
                        onMenuClick = {index->
                            when(index){
                                0->{
                                    homeEvent(MoneyEvent.SetMoney(money))
                                    isSheetState = true

                                }
                                1->{
                                    homeEvent(MoneyEvent.DeleteMoney(money))
                                }
                            }
                        }
                    )
                }
            }
            if (isSheetState){
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        isSheetState = false
                    },
                    containerColor = Color.LightGray
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TextField(
                            value = homeState.summa,
                            onValueChange = {
                                homeEvent(MoneyEvent.UpdateSumma(it))
                            },
                            singleLine = true,
                            placeholder = {
                                Text(text = "Narxi")
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
                                keyboardType = KeyboardType.Number
                            )
                        )
                        Spacer(Modifier.size(20.dp))
                        TextField(
                            value = homeState.text,
                            onValueChange = {
                                homeEvent(MoneyEvent.UpdateText(it))
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
                            placeholder = {
                                Text("Nomi")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                capitalization = KeyboardCapitalization.Words
                            )
                        )
                        Spacer(Modifier.size(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioWithText(
                                select = homeState.type == MoneyType.INCOME,
                                onSelected = {
                                    homeEvent(MoneyEvent.UpdateRadioButton(MoneyType.INCOME))
                                },
                                text = "Kirim"
                            )
                            Spacer(Modifier.size(15.dp))
                            RadioWithText(
                                select = homeState.type == MoneyType.EXPANSE,
                                onSelected = {homeEvent(MoneyEvent.UpdateRadioButton(MoneyType.EXPANSE))},
                                text = "Chiqim"
                            )
                        }
                        Spacer(Modifier.size(20.dp))

                        Button(
                            onClick = {
                                homeEvent(MoneyEvent.SaveMoney)
                                isSheetState = false
                            },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MoneyColor,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Save"
                            )
                        }
                        Spacer(Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MoneyHomePreview() {
    MoneyHome(
        rememberNavController(),
        homeState = MoneyState(),
        homeEvent = {}
    )
}