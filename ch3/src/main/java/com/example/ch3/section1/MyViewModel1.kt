package com.example.ch3.section1

import androidx.lifecycle.ViewModel

class MyViewModel1 : ViewModel() {
    // 내부 작성규칙은 따로 없다.
    var data = "hello"
    fun someFun(): String {
        return "someFun result"
    }
}