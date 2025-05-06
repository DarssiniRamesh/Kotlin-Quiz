package com.example.kotlinquiz.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

/**
 * Type converters for Room database
 * Converts complex types to and from simple types that Room can store
 */
class Converters {
    private val gson = Gson()
    
    /**
     * Convert a list of strings to a JSON string for storage in the database
     */
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }
    
    /**
     * Convert a JSON string from the database to a list of strings
     */
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
    
    /**
     * Convert a Date to a Long timestamp for storage in the database
     */
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
    
    /**
     * Convert a Long timestamp from the database to a Date
     */
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
    
    /**
     * Convert a Map<Int, Int> to a JSON string for storage in the database
     */
    @TypeConverter
    fun fromIntMap(map: Map<Int, Int>?): String? {
        if (map == null) return null
        return gson.toJson(map)
    }
    
    /**
     * Convert a JSON string from the database to a Map<Int, Int>
     */
    @TypeConverter
    fun toIntMap(value: String?): Map<Int, Int>? {
        if (value == null) return null
        val mapType = object : TypeToken<Map<Int, Int>>() {}.type
        return gson.fromJson(value, mapType)
    }
}
