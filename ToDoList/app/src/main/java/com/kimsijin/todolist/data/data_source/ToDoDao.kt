package com.kimsijin.todolist.data.data_source

import androidx.room.*
import com.kimsijin.todolist.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo ORDER BY date DESC")
    fun todos(): Flow<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: ToDo)

    @Update
    suspend fun update(todo: ToDo)

    @Delete
    suspend fun delete(todo: ToDo)

}