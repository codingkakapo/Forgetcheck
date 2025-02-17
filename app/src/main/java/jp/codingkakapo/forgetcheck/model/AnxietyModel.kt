package jp.codingkakapo.forgetcheck.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/*
    お出かけ前の心配事のモデル
 */
@Entity (tableName = "anxieties")
data class AnxietyModel (
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name : String,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime,
    var checked : Boolean //たぶん数字でなんとかする必要あり
)
{
    fun hoge() = ""
}