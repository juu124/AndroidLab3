package com.example.ch1.section1

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

// vs thread ... non-blocking
// runBlocking - main 함수내에서코투틴을 이용하기 위한 용도로만 제공한다.
// 범용적으로 runBlocking을 사용하진 않는다.
fun main() = runBlocking {
    val count = 10_000

    // 스레드를 활용
    // measureTimeMillis : { } 이 실행되는 시간을 측정하겠다.
    var time = measureTimeMillis {
        // 스레드가 10,000개가 필요하다는 가정
        val threadJobs = List<Thread>(count) {
            // 각각의 스레드는 아래의 작업을 수행한다.
            thread {
                Thread.sleep(1000)
                print(".")
            }
        }
        threadJobs.forEach { it.join() }    // 스레드가 종료될 때까지 대기하라는 의미 (10,000개의 스레드가 점찍고 하는 작업이 총 얼마나 걸릴지)
    }
    println()
    println("thread $count total time : $time")


    // 코루틴을 활용
    time = measureTimeMillis {
        // 스레드가 10,000개가 필요하다는 가정
        val coroutineJobs = List(count) {
            // 각각의 스레드는 아래의 작업을 수행한다.
            launch {    // 코루틴을 만드는 builder이다. 만들자 마자 구동한다.
                delay(1000)
                print(".")
            }
        }
        coroutineJobs.forEach { it.join() }    // 스레드가 종료될 때까지 대기하라는 의미
    }
    println()
    println("coroutine $count total time : $time")

    // thread 10000 total time : 3005
    // coroutine 10000 total time : 1068

    // 스레드는 진짜 만개를 만들었지만
    // 코틀린은 스레드를 만개를 만들지 않고 위의 작업을 처리했다,
    // 그래서 속도가 코틀린이 현저히 빠르다.
}

