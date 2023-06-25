package com.kimsijin.todolist.ui

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kimsijin.todolist.domain.model.ToDo
import com.kimsijin.todolist.domain.repository.ToDoRepository
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val toDoRepository: ToDoRepository
) : AndroidViewModel(application) {

    private val _items = mutableStateOf(emptyList<ToDo>())
    val items: State<List<ToDo>> = _items

    private var recentlyDeleteToDo: ToDo? = null

    fun addToDo(text: String) {
        viewModelScope.launch {
            toDoRepository.addToDo(ToDo(title = text))
        }
    }

    fun toggle(uid: Int) {
        val toDo = _items.value.find { toDo -> toDo.uid == uid }

        toDo?.let {
            viewModelScope.launch {
                toDoRepository.updateToDo(it.copy(isDone = !it.isDone).apply {
                    this.uid = it.uid
                })
            }
        }
    }

    fun delete(uid: Int) {
        val toDo = _items.value.find { toDo -> toDo.uid == uid }

        toDo?.let {
            viewModelScope.launch {
                toDoRepository.deleteToDo(it)
                recentlyDeleteToDo = it
            }
        }
    }

    fun restoreToDo() {
        viewModelScope.launch {
            toDoRepository.addToDo(recentlyDeleteToDo ?: return@launch)
            recentlyDeleteToDo = null
        }
    }

}