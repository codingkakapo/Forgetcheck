package jp.codingkakapo.forgetcheck.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class TypeConverter {

    @TypeConverter
    fun fromDateTime(time : LocalDateTime) : Long {
        return time.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toDateTime(value : Long) : LocalDateTime {
        return LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)
    }
}