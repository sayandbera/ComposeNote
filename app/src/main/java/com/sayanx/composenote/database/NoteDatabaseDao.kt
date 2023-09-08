package com.sayanx.composenote.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sayanx.composenote.model.Note
import kotlinx.coroutines.flow.Flow

/*
@Dao: This annotation tells Room that this interface is a DAO
@Query: This annotation is used to define a SQL query that the DAO can execute
*/

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * FROM notes_tbl")
    fun getNotes(): Flow<List<Note>>

    @Upsert()
    suspend fun upsert(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

}
