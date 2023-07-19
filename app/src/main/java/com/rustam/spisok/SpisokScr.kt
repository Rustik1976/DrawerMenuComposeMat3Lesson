package com.rustam.spisok

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.rustam.spisok.ui.theme.BackFon
import com.rustam.spisok.ui.theme.Purple200
import com.rustam.spisok.view_models.SpisokTovarovVM
import com.rustam.spisok.view_models.SpisokViewModel

@Composable
fun SpisokScr(
    navHostController: NavHostController,
    text: MutableState<String>,
    idd: MutableState<Int>,
    spisokViewModel: SpisokViewModel = viewModel(factory = SpisokViewModel.factory),
    spisokTovarovVM: SpisokTovarovVM = viewModel(factory = SpisokTovarovVM.factory)
) {
    val itemsList = spisokViewModel.itemsListSpisok.collectAsState(initial = emptyList())
    val err = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackFon)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = spisokViewModel.newText.value,
                onValueChange = { text ->
                    spisokViewModel.newText.value = text
                },
                label = {
                    Text("Наименование списка покупки")
                },
                placeholder = { Text(text = "Наименование") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = BackFon
                )
            )
//            LaunchedEffect(Unit) {
//                focusRequester.requestFocus()
//            }
            IconButton(
                onClick = {
                    if (spisokViewModel.newText.value.trim().isNotEmpty()) {
                        spisokViewModel.insertItem()
                        err.value = false
                    } else err.value = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier, tint = Purple200
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(itemsList.value) { item ->
                //idd.value = item.id!!
                SpisokItem(
                    text,
                    idd,
                    navHostController,
                    item,
                    {
                        spisokViewModel.spisokPokupok = it
                        spisokViewModel.newText.value = it.name
                    },
                    {
                        spisokViewModel.deleteItem(it)
                    },
                    spisokTovarovVM
                )
            }
        }
    }
    if (err.value) {
        focusRequester.requestFocus()
        Toast.makeText(
            LocalContext.current,
            "Наименование не должно быть пустым!",
            Toast.LENGTH_SHORT
        ).show()
    }
}