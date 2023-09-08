package com.sayanx.composenote.di

import android.content.Context
import androidx.room.Room
import com.sayanx.composenote.database.Database
import com.sayanx.composenote.database.NoteDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDao(database: Database) : NoteDatabaseDao {
        return database.noteDao()
    }

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context) : Database {
        return Room.databaseBuilder(
            context = context,
            klass = Database::class.java,
            name = "app_db"
        ).fallbackToDestructiveMigration().build()
    }

}