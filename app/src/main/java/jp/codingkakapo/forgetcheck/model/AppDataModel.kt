package jp.codingkakapo.forgetcheck.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "appdata")
data class AppDataModel (
    @PrimaryKey(autoGenerate = true) val id: Int,
    var lastLaunched : LocalDateTime
)

