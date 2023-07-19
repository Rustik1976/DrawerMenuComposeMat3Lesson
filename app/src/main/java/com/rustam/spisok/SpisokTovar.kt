package com.rustam.spisok

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rustam.spisok.data.SpisokTovarov
import com.rustam.spisok.data.Tovari
import com.rustam.spisok.ui.theme.ComboOption
import com.rustam.spisok.ui.theme.MenuTovar
import com.rustam.spisok.view_models.SpisokTovarovVM
import com.rustam.spisok.view_models.TovariVM
import java.math.RoundingMode


@Composable
fun SpisokTovar(

    text: MutableState<String>,
    idd: MutableState<Int>,
    openDialog: MutableState<Boolean>,
    spisokTovarovVM: SpisokTovarovVM = viewModel(factory = SpisokTovarovVM.factory),
    tovariVM: TovariVM = viewModel(factory = TovariVM.factory)
) {
    //var itemsList = spisokTovarovVM.itemsListSpisokTovarov.collectAsState(initial = emptyList())
    var itemsList = spisokTovarovVM.getAll(idd.value).collectAsState(initial = emptyList())
    val itemsListTovar = tovariVM.itemsListTovari.collectAsState(initial = emptyList())
    val listAdd = ArrayList<SpisokTovarov>()
    val listDel = ArrayList<SpisokTovarov>()
    val isCheck = remember { mutableStateOf(false) }
    val isCheckUp = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
                MenuTovar(
                    labelText = text.value,
                    options = convertList(itemsListTovar.value),
                    onOptionsChosen = {

                        listAdd.clear()
                        listDel.clear()
                        it.forEach {
                            listAdd.add(
                                SpisokTovarov(
                                    null,
                                    idd.value,
                                    it.text,
                                    "",
                                    false
                                )
                            )
                        }
                        for (i in 0..listAdd.size - 1) {
                            for (z in 0..itemsList.value.size - 1) {
                                if (itemsList.value[z].name == listAdd[i].name) {
                                    listAdd[i].id = itemsList.value[z].id
                                    listAdd[i].kol = itemsList.value[z].kol
                                    listAdd[i].status = itemsList.value[z].status
                                }
                            }
                        }

                        for (i in 0..itemsList.value.size - 1) {
                            var ll = false
                            for (z in 0..listAdd.size - 1) {
                                if (listAdd[z].name == itemsList.value[i].name) {
                                    ll = true
                                }
                            }
                            if (!ll) {
                                listDel.add(itemsList.value[i])
                            }
                        }

                        listAdd.forEach {
                            spisokTovarovVM.spisokTovarov = it
                            spisokTovarovVM.insertItem(it)
                            //Log.d("MyLog","Add Item: $it")
                        }
                        listDel.forEach {
                            spisokTovarovVM.spisokTovarov = it
                            spisokTovarovVM.deleteItem(it)
                            Log.d("MyLog","Del Item: $it")
                        }
                        isCheckUp.value = true
                    },
                    selectedIds = convertListChek(itemsList.value),
                    modifier = Modifier.fillMaxWidth()
                )
        }
        val prg = remember { mutableFloatStateOf( 0f ) }
        LinearProgressIndicator(
            progress = prg.value,
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            color = Color.Green,
            backgroundColor = Color.Red
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            var count = 0
            items(itemsList.value) { item ->
                if (item.status) count++
                val aa = ((100f / itemsList.value.size.toFloat()) * count.toFloat()).toInt()
                    .toBigDecimal().setScale(0, RoundingMode.UP).toFloat()
                if(aa.toString().substring(0,2) == "10") {
                    prg.value = ("${aa.toString().substring(0, 1)}.0f").toFloat()
                }
                else {
                    prg.value = ("0.${aa.toString().substring(0, 1)}f").toFloat()
                }
                SpisokTovarItem(
                    openDialog,
                    spisokTovarovVM,
                    item,
                    {
                        spisokTovarovVM.deleteItem(it)
                    },
                    {
                        spisokTovarovVM.insertItem(it)
                        isCheck.value = true
                    }
                )
            }
        }

    }
    if (isCheck.value) {
        itemsList = spisokTovarovVM.getAll(idd.value).collectAsState(initial = emptyList())
        isCheck.value = false
    }
    if (isCheckUp.value) {
        itemsList = spisokTovarovVM.getAll(idd.value).collectAsState(initial = emptyList())
        isCheckUp.value = false
    }
}

fun convertList(itemListTovari: List<Tovari>): List<ComboOption> {
    if (itemListTovari.size == 0) return emptyList()
    val listCombo = ArrayList<ComboOption>()
    itemListTovari.forEach { item ->
        listCombo.add(
            ComboOption(
                item.name,// + " | " + item.id.toString(),
                item.id ?: 0
            )
        )
    }
    return listCombo
}


fun convertListChek(itemListTovari: List<SpisokTovarov>): List<String> {
    if (itemListTovari.size == 0) return emptyList()
    val listInt = ArrayList<String>()
    itemListTovari.forEach { item ->
        //Log.d("MyLog", "Id = ${item.id}")
        listInt.add(item.name)
    }
    return listInt
}