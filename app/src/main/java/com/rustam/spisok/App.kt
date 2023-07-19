package com.rustam.spisok

import android.app.Application
import com.rustam.spisok.data.MainDb


class App : Application(){
    val database by lazy { MainDb.createDatebase(this) }
}