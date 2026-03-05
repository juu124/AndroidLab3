package com.example.ch3.section3

import androidx.room.Entity
import androidx.room.PrimaryKey

// entity 클래스이다.
// 데이터 추상화 클래스... 데이터베이스와 연계..
// 이 클래스 데이터를 저장하기 위한 테이블이 자동으로 만들어 진다.
// 클래스 명으로 테이블명이...
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var lastName: String,
    var firstName: String,
    var email: String?
)

