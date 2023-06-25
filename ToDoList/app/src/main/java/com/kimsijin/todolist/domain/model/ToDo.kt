package com.kimsijin.todolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class ToDo(
    val title: String,
    val date: Long = Calendar.getInstance().timeInMillis,
    val isDone: Boolean = false,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
