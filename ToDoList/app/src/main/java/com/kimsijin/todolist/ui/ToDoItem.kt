package com.kimsijin.todolist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.kimsijin.todolist.R
import com.kimsijin.todolist.domain.model.ToDo
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ToDoItem(
    toDo: ToDo,
    onClick: (todo: ToDo) -> Unit = {},
    onDelete: (todo: ToDo) -> Unit = {},
) {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Column(
        Modifier
            .padding(8.dp)
            .clickable { onClick(toDo) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onDelete(toDo) },
                painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                contentDescription = null,
                tint = Color(0xFFA51212),
            )

            Column(
                modifier = Modifier.weight(1f),
            ) {

                Text(
                    format.format(Date(toDo.date)),
                    color = if (toDo.isDone) Color.Gray else Color.Black,
                    style = TextStyle(textDecoration = if (toDo.isDone) TextDecoration.LineThrough else TextDecoration.None),
                )
                Text(
                    toDo.title,
                    color = if (toDo.isDone) Color.Gray else Color.Black,
                    style = TextStyle(textDecoration = if (toDo.isDone) TextDecoration.LineThrough else TextDecoration.None),
                )
            }

            if (toDo.isDone) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_done_24),
                    contentDescription = null,
                    tint = Color(0xFF00BCD4),
                )
            }
        }
    }
}