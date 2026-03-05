package com.example.ch3.section3

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

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

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var street: String,
    var state: String,
    var city: String
)

@Entity
data class Customer(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var address: Address,     // 기초타입의 변수가 아니기 때문에 Room이 자동으로 해석 못함 Converter 필요
    var datas: List<String>,  // 기초타입의 변수가 아니기 때문에 Room이 자동으로 해석 못함 Converter 필요
    var regData: Date         // 기초타입의 변수가 아니기 때문에 Room이 자동으로 해석 못함 Converter 필요
)

