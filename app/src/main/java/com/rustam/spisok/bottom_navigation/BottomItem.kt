package com.rustam.spisok.bottom_navigation

import com.rustam.spisok.OPTIONS
import com.rustam.spisok.R
import com.rustam.spisok.SPISOK_POKUPOK
import com.rustam.spisok.TOVAR
import com.rustam.spisok.WORK_DAY
import com.rustam.spisok.ZAMETKI

sealed class BottomItem(val title: String, val iconId: Int, val route: String){
    object Spisok: BottomItem("Покупки",R.drawable.ic_spisok, SPISOK_POKUPOK)
    object Zametki: BottomItem("Заметки",R.drawable.ic_notebook, ZAMETKI)
    object Tovari: BottomItem("Товары",R.drawable.ic_tovar, TOVAR)
    object Work: BottomItem("Работа", R.drawable.ic_work, WORK_DAY)
}
