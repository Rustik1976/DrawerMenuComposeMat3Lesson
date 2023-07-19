package com.rustam.spisok

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.rustam.spisok.data.NoteBook
import com.rustam.spisok.ui.theme.BackMenu
import com.rustam.spisok.ui.theme.Purple200
import com.rustam.spisok.view_models.NoteBookVM

@Composable
fun EditDescNoteBook(
    noteBookVM: NoteBookVM,
    openDialog: MutableState<Boolean>,
    item: NoteBook
) {
    val focusRequester = remember { FocusRequester() }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    color = Purple200,
                    text = "Введите описание"
                )
            },
            text = {
                OutlinedTextField(
                    value = noteBookVM.newDesc.value,
                    onValueChange = { text ->
                        noteBookVM.newDesc.value = text
                    },
                    label = {
                        Text("Описание заметки")
                    },
                    placeholder = { Text(text = "Описание") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            item.desc = noteBookVM.newDesc.value
                            noteBookVM.updatedesc(item)

                            openDialog.value = false
                        }
                    ) {
                        Text("Сохранить")
                    }
                    Button(
                        onClick = { openDialog.value = false }
                    ) {
                        Text("Отмена")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentWidth()
                .wrapContentHeight(),
            backgroundColor = BackMenu
        )
    }
}