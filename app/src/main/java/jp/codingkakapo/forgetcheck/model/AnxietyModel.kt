package jp.codingkakapo.forgetcheck.model

import java.time.LocalDateTime

/*
    お出かけ前の心配事のモデル
 */
data class AnxietyModel (
    val name : String,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime,
    val checked : Boolean
)
{
    fun hoge() = ""
}