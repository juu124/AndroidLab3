package com.example.ch1.section8

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    // 발행할 데이터 준비
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // 짝수만 제곱으로 3개만 받아오고 싶다.
    list.asFlow()
        .filter {      // 짝수만
            it % 2 == 0
        }
        .map {          // 제곱으로
            it * it
        }
        .take(3)    // 3개만 받아오기
        .collect {  // 구독자
            println(it)
        }
    //4
    //16
    //36


    fun something() : Flow<Int> = flow {
        // 나는 cold stream 이다. 내가 실행될 때 실행시키는 thread는?
        println("flow thread : ${Thread.currentThread().name}")
        repeat(3) {
            delay(100)
            emit(it)
        }
    }
        // flow를 실행시키는 thread를 교체하고 싶은 경우
        .flowOn(Dispatchers.Default)

    println("main thread : ${Thread.currentThread().name}")
    something().collect { println(it) }

    // 기본은 collect의 thread에 의해 flow 동작하게끔 할 수 있다.
    //main thread : main
    //flow thread : main

    //flowOn을 지정한 경우 : 다른 thread로 교체했다.
    //main thread : main
    //flow thread : DefaultDispatcher-worker-1
}