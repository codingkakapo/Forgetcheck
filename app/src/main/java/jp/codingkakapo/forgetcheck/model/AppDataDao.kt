package jp.codingkakapo.forgetcheck.model

import androidx.room.*


@Dao
interface AppDataDao {

    @Query("select * from appdata")
    fun selectAll() : List<AppDataModel>

    @Insert
    fun insert(appData: AppDataModel)

    @Delete
    fun delete(appData: AppDataModel)

    @Update
    fun update(appData: AppDataModel)
}