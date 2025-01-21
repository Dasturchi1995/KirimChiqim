package com.example.kirim_chiqim.navigation.component

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomDropdownMenu(
    modifier: Modifier = Modifier,
    showMenu: Boolean = false,
    menuItems: List<String>,
    onDismiss: ()->Unit,
    onMenuClick: (index: Int)->Unit
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismiss,
        modifier = modifier
            .background(Color.White)
    ) {
        menuItems.forEachIndexed { index, s ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = s,
                        color = Color.Black
                    )
                },
                onClick = {
                    onMenuClick(index)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CustomDropdownMenu (
        onMenuClick = {},
        onDismiss = {},
        showMenu = true,
        menuItems = listOf("Update", "Delete")
    )
}