package com.example.ch3.section3

import androidx.room.Database
import androidx.room.RoomDatabase

// 앱 내에 Entity 클래스는 여러개
// 앱 내에 DAO 도 여러개
// Entity, DAO 등록...

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    // DAO 를 획득하기 위한 함수 선언
    abstract fun userDao(): UserDAO
}