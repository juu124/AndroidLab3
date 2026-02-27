package com.example.ch1.section8

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

// 업무 복잡하다
// 여러 flow 나왔다.
// 개별적으로 실행되어 각각 데이터를 발행하기는 하지만
// 구독자 입장에서 여러 flow를 결합시켜 하나의 flow 데이터 처럼 활용하고 싶다.
val flow1 = flow {
    for (i in 1..5) {
        delay(100)
        println("flow1 emit $i")
        emit(i)
    }
}

val flow2 = flow {
    for (i in 101..105) {
        delay(100)
        println("flow2 emit $i")
        emit(i)
    }
}

fun main() = runBlocking {
    // zip ..................
//    flow1.zip(flow2) { a, b ->
//        "$a - $b"
//    }.collect {
//        println(it)
//    }

    // combine..................
    flow1.combine(flow2) { a, b ->
        "$a - $b"
    }.collect {
        println(it)
    }
}

// zip : 2 flow 모두 신규 데이터를 발행해야 움직인다.
//flow1 emit 1
//flow2 emit 101
//1 - 101
//flow2 emit 102
//flow1 emit 2
//2 - 102
//flow1 emit 3
//flow2 emit 103
//3 - 103
//flow2 emit 104
//flow1 emit 4
//4 - 104
//flow1 emit 5
//flow2 emit 105
//5 - 105

//combine
//flow1 emit 1
//flow2 emit 101
//1 - 101
//flow1 emit 2
//flow2 emit 102
//2 - 102
//flow1 emit 3
//flow2 emit 103
//3 - 103
//flow1 emit 4
//flow2 emit 104
//4 - 104
//flow1 emit 5
//flow2 emit 105
//5 - 105
