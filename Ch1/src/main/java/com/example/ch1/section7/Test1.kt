package com.example.ch1.section7

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    // cold stream
    fun something1(): Flow<Int> = flow {
        repeat(3) {
            delay(100)
            emit(it)
        }
    }

    val flow = something1()
    val data1 = flow.first()// 구독자
    println("first : $data1")
    //first : 0
    // first(순서상 첫번째)에 의해 데이터 구독이 완료되면 구독자는 사라진 것이다. flow 자동 취소이다. : cold Stream

    // 순서상 첫번째가 아니라 내 조건을 만족하는 첫번째
    val data2 = flow.first {
        // 어떤 조건에 의한 첫번째 인지 코딩
        println(it)
        it % 2 != 0
    }
    println("first2 : $data2")
    //0
    //1
    //first2 : 1

    // 집합데이터를 발행하겠다.
    // reduce, fold, 새로운 데이터를 전달하면서 이 함수에서 이전에 리턴시킨 데이터를 같이 전달하라..
    val result1 = listOf(1, 2, 3).asFlow().reduce { a, b -> a + b}
    // 1, 2 ==> 3
    // 3, 3 ==> 6

    val result2 = listOf(1, 2, 3).asFlow().fold(0) { a, b -> a + b}
    // 0, 1 ==> 1
    // 1, 2 ==> 3
    // 3, 3 == > 6
    println("$result1, $result2") // 6, 6

    delay(1000)
}