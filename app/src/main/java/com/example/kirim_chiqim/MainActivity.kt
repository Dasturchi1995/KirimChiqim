package com.example.kirim_chiqim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kirim_chiqim.modul.Money
import com.example.kirim_chiqim.navigation.MoneyEvent
import com.example.kirim_chiqim.navigation.MoneyViewModel
import com.example.kirim_chiqim.navigation.home.MoneyHome
import com.example.kirim_chiqim.presintation.MoneyNavType
import com.example.kirim_chiqim.presintation.Screen
import com.example.kirim_chiqim.ui.theme.KirimchiqimTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KirimchiqimTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.MoneyHome
                ){
                    composable<Screen.MoneyHome>(
                        typeMap = mapOf(
                            typeOf<Money>() to MoneyNavType.MoneyNavType
                        )
                    ) {

                        val viewModel = hiltViewModel<MoneyViewModel>()
                        val state by viewModel.state.collectAsState()
                        val event = viewModel::onEvent

                        MoneyHome(
                            navController = navController,
                            homeState = state,
                            homeEvent = event
                        )
                    }
                }
            }
        }
    }
}