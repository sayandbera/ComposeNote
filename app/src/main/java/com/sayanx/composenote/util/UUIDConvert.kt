package com.sayanx.composenote.util

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConvert {
    @TypeConverter
    fun fromUUID(uuid: UUID): String? {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID? {
        return UUID.fromString(string)
    }
}