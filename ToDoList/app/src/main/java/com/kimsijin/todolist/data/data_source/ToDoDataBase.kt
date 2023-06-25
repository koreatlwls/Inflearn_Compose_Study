package com.kimsijin.todolist.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kimsijin.todolist.domain.model.ToDo

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDataBase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

}