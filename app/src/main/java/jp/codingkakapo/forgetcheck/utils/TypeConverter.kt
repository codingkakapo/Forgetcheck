package jp.codingkakapo.forgetcheck.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime

class TypeConverter {

    // ToDo 仮実装　あとでまともに作れ
    @TypeConverter
    fun fromDateTime(time : LocalDateTime) : Long {
        return 0
    }

    @TypeConverter
    fun toDateTime(value : Long) : LocalDateTime {
        return LocalDateTime.now()
    }
}