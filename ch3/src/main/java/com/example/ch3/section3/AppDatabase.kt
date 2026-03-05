package com.example.ch3.section3

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// table scheme 변경과 적용. 버전이 중요하다.
val migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE USER ADD COLUMN email TEXT DEFAULT null")
    }
}


// 앱 내에 Entity 클래스는 여러개
// 앱 내에 DAO 도 여러개
// Entity, DAO 등록...

@Database(entities = arrayOf(User::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    // DAO 를 획득하기 위한 함수 선언
    abstract fun userDao(): UserDAO
}