package com.sayanx.composenote.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sayanx.composenote.model.Note
import com.sayanx.composenote.util.UUIDConvert

@Database(entities = [Note::class], version = 6, exportSchema = true)
@TypeConverters(UUIDConvert::class)
abstract class Database : RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}