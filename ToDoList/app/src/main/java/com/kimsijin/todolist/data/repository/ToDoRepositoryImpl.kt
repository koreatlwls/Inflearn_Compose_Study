package com.kimsijin.todolist.data.repository

import android.app.Application
import androidx.room.Room
import com.kimsijin.todolist.data.data_source.ToDoDataBase
import com.kimsijin.todolist.domain.model.ToDo
import com.kimsijin.todolist.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(application: Application) : ToDoRepository {

    private val db = Room.databaseBuilder(
        application,
        ToDoDataBase::class.java,
        "todo-db"
    ).build()

    override fun observeToDos(): Flow<List<ToDo>> {
        return db.toDoDao().todos()
    }

    override suspend fun addToDo(toDo: ToDo) {
        db.toDoDao().insert(toDo)
    }

    override suspend fun updateToDo(toDo: ToDo) {
        db.toDoDao().update(toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        db.toDoDao().delete(toDo)
    }

}