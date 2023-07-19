package com.rustam.spisok

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rustam.spisok.data.SpisokTovarov
import com.rustam.spisok.ui.theme.Pink20
import com.rustam.spisok.ui.theme.Pink40
import com.rustam.spisok.ui.theme.Purple120
import com.rustam.spisok.ui.theme.Purple150
import com.rustam.spisok.ui.theme.Purple40
import com.rustam.spisok.ui.theme.PurpleGrey80
import com.rustam.spisok.view_models.SpisokTovarovVM

@Composable
fun SpisokTovarItem(
    openDialog: MutableState<Boolean>,
    spisokTovarovVM: SpisokTovarovVM,
    item: SpisokTovarov,
    onClickDelete: (SpisokTovarov) -> Unit,
    onClickCheck: (SpisokTovarov) -> Unit
) {
    val colState = remember {mutableStateOf(PurpleGrey80)}
    if (item.status) colState.value = Purple40 else colState.value = PurpleGrey80
    spisokTovarovVM.spisokTovarov = item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                if (!item.status) {
                    openDialog.value = true
                    spisokTovarovVM.spisokTovarov = item
                    spisokTovarovVM.newKol.value = item.kol
                }
            },
        backgroundColor = colState.value
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
                text = item.kol,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp),
                color = Pink20
            )
            val checkedState = remember { mutableStateOf(false) }
            checkedState.value = item.status
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    item.status = it
                    onClickCheck(item)
                },
                colors  = CheckboxDefaults.colors(
                    checkedColor = Purple120,
                    checkmarkColor = Color.Black
                )
            )
            IconButton(
                onClick = {
                    if (!item.status) onClickDelete(item)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier, tint = Purple150
                )
            }
        }
    }
    if (openDialog.value) EditTovarsKol(spisokTovarovVM = spisokTovarovVM, openDialog = openDialog, item = item)
}