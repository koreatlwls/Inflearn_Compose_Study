package com.kimsijin.todolist.domain.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kimsijin.todolist.data.repository.ToDoRepositoryImpl
import com.kimsijin.todolist.domain.repository.ToDoRepository
import com.kimsijin.todolist.ui.MainViewModel

class ToDoAndroidViewModelFactory(
    private val application: Application,
    private val repository: ToDoRepository = ToDoRepositoryImpl(application),
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                application,
                repository
            ) as T
        }
        return super.create(modelClass)
    }

}