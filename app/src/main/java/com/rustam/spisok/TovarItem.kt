package com.rustam.spisok

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rustam.spisok.data.Tovari
import com.rustam.spisok.ui.theme.Pink40
import com.rustam.spisok.ui.theme.Purple120
import com.rustam.spisok.ui.theme.Purple150
import com.rustam.spisok.ui.theme.PurpleGrey80

@Composable
fun TovarItem(
    item: Tovari,
    onClick: (Tovari) -> Unit,
    onClickDelete: (Tovari) -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {

            },
        backgroundColor = PurpleGrey80
    ) {
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
    }
}