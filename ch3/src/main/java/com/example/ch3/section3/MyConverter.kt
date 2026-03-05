package com.example.ch3.section3

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class MyConverter {
    // 함수명을 의미가 없다. 어노테이션과 매개변수 타입, 리턴 타입을 보고 어느 함수를 이용할지 자동 결정한다.
    @TypeConverter
    fun listToString(value: List<String>?): String {    // insert 시에 즉 저장시에 호출
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToList(value: String): List<String> {     // select 시에 즉, 획득시에 호출
        val type = object: TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
//        return Gson().fromJson(value, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun timeToDate(value: Long?): Date? {
        return value?.let { Date(it)}
    }

    @TypeConverter
    fun dateToTime(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun stringToAddress(value: String): Address {
        val type = object : TypeToken<Address>(){}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun addressToString(address: Address): String {
        return Gson().toJson(address)
    }
}