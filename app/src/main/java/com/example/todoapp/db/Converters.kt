package com.example.todoapp.db

import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date

class Converters {

    @TypeConverter
    fun fromCalendar(date: Calendar) : Long {
        return date.timeInMillis
    }
    @TypeConverter
    fun toCalendar(timeInMillis: Long) : Calendar{
        return Calendar.getInstance().apply {
            this.timeInMillis = timeInMillis
        }
    }
}