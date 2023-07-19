package com.rustam.spisok

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.rustam.spisok.bottom_navigation.MainScreen
import com.rustam.spisok.ui.theme.SpisokTheme

const val SPISOK_POKUPOK = "Spisok_pokupok"
const val SPISOK_TOVAROV = "Spisok_tovarov"
const val ZAMETKI = "Zametki"
const val TOVAR = "Tovari"
const val OPTIONS = "Options"
const val WORK_DAY = "Work_day"

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ttt = remember {mutableStateOf("")}
            val idd = remember { mutableIntStateOf(0) }
            val openDialog = remember { mutableStateOf(false) }
            SpisokTheme {
                MainScreen(ttt,idd,openDialog)
            }
        }
    }
}

