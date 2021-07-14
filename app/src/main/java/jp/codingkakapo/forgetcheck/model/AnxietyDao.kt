package jp.codingkakapo.forgetcheck.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnxietyDao {

    @Query("select * from anxieties")
    fun selectAll() : List<AnxietyModel>

    @Insert
    fun insert(anxiety: AnxietyModel)

    @Delete
    fun delete(anxiety: AnxietyModel)

    @Update
    fun update(anxiety: AnxietyModel)
}