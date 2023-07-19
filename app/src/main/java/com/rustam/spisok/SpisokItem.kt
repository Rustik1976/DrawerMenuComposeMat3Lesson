package com.rustam.spisok

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rustam.spisok.data.SpisokPokupok
import com.rustam.spisok.data.SpisokTovarov
import com.rustam.spisok.ui.theme.Pink40
import com.rustam.spisok.ui.theme.Purple120
import com.rustam.spisok.ui.theme.Purple150
import com.rustam.spisok.ui.theme.PurpleGrey80
import com.rustam.spisok.view_models.SpisokTovarovVM
import java.math.RoundingMode

@Composable
fun SpisokItem(
    text: MutableState<String>,
    idd: MutableState<Int>,
    navHostController: NavHostController,
    item: SpisokPokupok,
    onClick: (SpisokPokupok) -> Unit,
    onClickDelete: (SpisokPokupok) -> Unit,
    spisokTovarovVM: SpisokTovarovVM //= viewModel(factory = SpisokTovarovVM.factory)
) {
    val navController = navHostController
    var itemsList: State<List<SpisokTovarov>>

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                text.value = item.name
                idd.value = item.id!!
                spisokTovarovVM.newText.value = item.name
                navController.navigate(SPISOK_TOVAROV)
                // Log.d("MyLog","Name = ${item.name}")
            },
        backgroundColor = PurpleGrey80
    ) {
        Column() {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(10.dp),
                    color = Pink40
                )
                Text(
                    text = item.date,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp),
                    fontSize = 10.sp,
                    color = Color.Blue
                )

                IconButton(
                    onClick = {
                        onClick(item)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier, tint = Purple120
                    )
                }
                IconButton(
                    onClick = {
                        onClickDelete(item)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier, tint = Purple150
                    )
                }
            }

            val prg1 = remember { mutableFloatStateOf(0f) }
            LinearProgressIndicator(
                progress = prg1.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                color = Color.Green,
                backgroundColor = Color.Red
            )
            var count = 0
            prg1.value = 0f
            itemsList = spisokTovarovVM.getAll(item.id!!).collectAsState(initial = emptyList())
            for (i in 0..itemsList.value.size - 1) {
                if (itemsList.value[i].status) count++
                val aa = ((100f / itemsList.value.size.toFloat()) * count.toFloat()).toInt()
                    .toBigDecimal().setScale(0, RoundingMode.UP).toFloat()
                if (aa.toString().substring(0, 2) == "10") {
                    prg1.value = ("${aa.toString().substring(0, 1)}.0f").toFloat()
                } else {
                    prg1.value = ("0.${aa.toString().substring(0, 1)}f").toFloat()
                }
            }
        }
    }
}