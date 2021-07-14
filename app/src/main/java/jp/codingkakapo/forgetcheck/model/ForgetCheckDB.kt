package jp.codingkakapo.forgetcheck.model

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.codingkakapo.forgetcheck.utils.TypeConverter
import java.lang.Exception

@Database(entities = arrayOf(AnxietyModel::class), version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class ForgetCheckDB() : RoomDatabase() {

    abstract fun AnxietyDao() : AnxietyDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ForgetCheckDB? = null

        fun getDB(context: Context): ForgetCheckDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ForgetCheckDB::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

/*
    companion object{
        //singleton
        private var instance : ForgetCheckDB? = null
        fun getDB(c : Context) : ForgetCheckDB{
            if(instance != null)
                return instance as ForgetCheckDB
            else
                instance = Room.databaseBuilder(c, ForgetCheckDB::class.java, "ForgetCheckDB").build()
            val v = instance
                throw
                    Exception("exception!")
        }
    }
*/
}