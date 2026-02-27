package com.example.ch1.section8

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val list = listOf(1, 3, -2, 10, 12, -5)

    // 플로우가 되었다.
    list.asFlow()
        .transform { // 다른 Intermediate operator랑 다르게 operator가 실행되면서 이 안에서 emit() 함수 호출이 가능하다.
            if (it > 0) {
                if (it % 2 == 0) {
                    emit("Even number $it")
                } else {
                    emit("Odd number $it")
                }
            }
        }
        // 구독자
        .collect { println(it) }
    //Odd number 1
    //Odd number 3
    //Even number 10
    //Even number 12

    fun something() = flow {
        repeat(3) {
            delay(100)
            emit(it)
            println("emit $it")
        }
    }
    // 발행자의 속도가 구독자보다 빠르다
    something()
        .buffer(5)
        .conflate()
        .collect {
            println("collect $it")
            delay(300)
        }
}

// 기본 (operator를 추가하지 않음) => 발생과 구독이 순차적으로.. 구독자의 속도에 맞추어진다.
//collect 0
//emit 0
//collect 1
//emit 1
//collect 2
//emit 2

// buffer(5)........................
//emit 0
//collect 0
//emit 1
//emit 2
//collect 1
//collect 2

// buffer(5)........................
// conflate()...................... 버퍼에 미리 쌓여져 있다고 해도(이전 발행 데이터) 구독자가 구독하는 순간의 최신 데이터만 사용
//emit 0
//collect 0
//emit 1
//emit 2
//collect 2