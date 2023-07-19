package com.rustam.spisok.bottom_navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    text: MutableState<String>,
    idd: MutableState<Int>,
    openDialog: MutableState<Boolean>
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            KnopkiNavigation(navController = navController)
        }
    ) {
        NavGraph(navHostController = navController, text, idd, openDialog)
    }
}