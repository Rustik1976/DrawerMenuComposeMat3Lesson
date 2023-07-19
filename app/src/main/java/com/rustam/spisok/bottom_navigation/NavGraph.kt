package com.rustam.spisok.bottom_navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rustam.spisok.SPISOK_POKUPOK
import com.rustam.spisok.SPISOK_TOVAROV
import com.rustam.spisok.SpisokNoteBook
import com.rustam.spisok.SpisokScr
import com.rustam.spisok.SpisokTovar
import com.rustam.spisok.TOVAR
import com.rustam.spisok.Tovari
import com.rustam.spisok.WORK_DAY
import com.rustam.spisok.WorkScr
import com.rustam.spisok.ZAMETKI

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navHostController: NavHostController,
    text: MutableState<String>,
    idd: MutableState<Int>,
    openDialog: MutableState<Boolean>
) {

    NavHost(navController = navHostController, startDestination = SPISOK_POKUPOK) {
        composable(SPISOK_POKUPOK) {
            SpisokScr(navHostController, text, idd)
        }
        composable(ZAMETKI) {
            SpisokNoteBook(openDialog)
        }
        composable(TOVAR) {
            Tovari()
        }
        composable(WORK_DAY) {
            WorkScr()
        }
        composable(SPISOK_TOVAROV) {
            SpisokTovar(text, idd, openDialog)
        }
    }
}
