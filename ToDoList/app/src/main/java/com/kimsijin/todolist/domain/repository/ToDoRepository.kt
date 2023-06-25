package com.kimsijin.todolist.domain.repository

import com.kimsijin.todolist.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun observeToDos(): Flow<List<ToDo>>

    suspend fun addToDo(toDo: ToDo)

    suspend fun updateToDo(toDo: ToDo)

    suspend fun deleteToDo(toDo: ToDo)

}