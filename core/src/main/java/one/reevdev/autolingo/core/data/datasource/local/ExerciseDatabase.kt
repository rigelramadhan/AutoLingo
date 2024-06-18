package one.reevdev.autolingo.core.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import one.reevdev.autolingo.core.data.datasource.local.dao.ExerciseDao
import one.reevdev.autolingo.core.data.datasource.local.model.ChatHistory

@Database(entities = [ChatHistory::class], version = 2, exportSchema = true)
abstract class ExerciseDatabase : RoomDatabase() {
    abstract fun getDao(): ExerciseDao
}