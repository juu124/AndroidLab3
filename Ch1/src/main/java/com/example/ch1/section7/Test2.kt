package com.example.ch1.section7

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    fun something1(): Flow<Int> = flow {
        repeat(3) {
            delay(100)
            emit(it)
        }
    }

    val flow = something1()
    println("step1")
    // 구독하기
    // flow 구독은 blocking이다. 모든 구독이 완료되어야 밑줄이 실행된다.
    flow.collect { // 여기 구문이 다 완료되야 다음줄이 실행됨
        println("test1 $it")
    }
    println("step2")

    // step2가 찍힌 위치를 보면 좋다.
    //step1
    //test1 0
    //test1 1
    //test1 2
    //step2

    // flow 구독을 non-blocking으로 처리하겠다면 코루틴 내에서 구독되게끔 하면된다.
    launch {    // 코루틴으로 묶으면 구독에 의한 blocking 해결가능
        flow.collect {
            println("test2 $it")
        }
    }
    println("step3")
    //step3
    //test2 0
    //test2 1
    //test2 2

    // 위의 코드를 아래처럼 작성할 수도 있다. (이 형식으로 많이 작성한다.)
    flow.onEach {
        println("test3 $it")
    }.launchIn(this)    // launchIn : 코루틴 builder 이자. flow terminal operator이다.
    // launchIn을 했다는 것은 코루틴이 만들어지고, 그 코루틴에 의해 flow데이터가 구독되게 하고 있다.
    // 매개변수는 scope
    println("step4")

    delay(2000)
}