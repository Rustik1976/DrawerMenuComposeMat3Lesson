package com.rustam.spisok.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ComboOption(
    override val text: String,
    val id: Int,
) : SelectableOption

interface SelectableOption {
    val text: String
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTovar(
    labelText: String,
    options: List<ComboOption>,
    onOptionsChosen: (List<ComboOption>) -> Unit,
    modifier: Modifier = Modifier,
    selectedIds: List<String> = emptyList()

) {
    var checked = true
    var expanded by remember { mutableStateOf(false) }
    // when no options available, I want ComboBox to be disabled
    val isEnabled by rememberUpdatedState { options.isNotEmpty() }
    var selectedOptionsList = remember {
        derivedStateOf {
            mutableStateListOf<String>()
        }
    }.value
    var selectedSummary = ""

    //Initial setup of selected ids
    //selectedOptionsList.clear()
    selectedIds.forEach {
        //if (it !in selectedOptionsList)
        selectedOptionsList.add(it)
    }

//    temp = selectedOptionsList.toList().distinct() as ArrayList<String>
//    selectedOptionsList = temp.toList() as SnapshotStateList<String>

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (isEnabled()) {
                expanded = !expanded
                //if (!expanded) {
                //onOptionsChosen(options.filter { it.text in selectedOptionsList }.toList())
                //}
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)

    ) {
        selectedSummary = when (options.filter { it.text in selectedOptionsList }.toList().size) {
            0 -> ""
            1 -> options.first { it.text == selectedOptionsList.first() }.text
            else -> "Выбрано ${options.filter { it.text in selectedOptionsList }.toList().size}"

        }
        TextField(
            enabled = isEnabled(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = selectedSummary,
            onValueChange = {},
            label = { Text(text = labelText) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = Pink40, containerColor = PurpleGrey80
            )
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PurpleGrey80),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                onOptionsChosen(options.filter { it.text in selectedOptionsList }.toList())
            },
        ) {
            for (option in options) {

                //use derivedStateOf to evaluate if it is checked
                checked = remember {
                    derivedStateOf { option.text in selectedOptionsList }
                }.value
                //val checked =   rememberUpdatedState(newValue = option.text in selectedOptionsList).value
                DropdownMenuItem(
                    modifier = Modifier.fillMaxSize(),
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { newCheckedState ->
                                    if (newCheckedState) {
                                        selectedOptionsList.add(option.text)
                                    } else {
                                        for (i in 0..selectedOptionsList.size - 1) {
                                            selectedOptionsList.remove(option.text)
                                        }

                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Purple100,
                                    uncheckedColor = Purple80
                                )
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = option.text
                            )

                        }
                    },
                    onClick = {
                        if (!checked) {
                            selectedOptionsList.add(option.text)
                        } else {
                            for (i in 0..selectedOptionsList.size - 1) {
                                selectedOptionsList.remove(option.text)
                            }
                        }
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )

            }
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        modifier = Modifier.fillMaxWidth().padding(5.dp),
                        onClick = {
                            expanded = false
                            onOptionsChosen(options.filter { it.text in selectedOptionsList }
                                .toList())
                        }
                    ) {
                        Text(text = "Добавить")
                    }
                }
            }
        }
    }
}