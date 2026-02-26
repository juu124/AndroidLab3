package com.example.ch1.section4

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

// withContext, withTimeout...
fun main() = runBlocking {
    val job = launch {
        // withContext : 코루틴 실행 영역중에서 일부분의 context 정보 고체가 목적이다.
        withContext(NonCancellable) {
            repeat(3) {
                delay(300)
                println("job.. $it")
            }
        }
    }

    delay(200)
    job.cancelAndJoin()

    //job.. 0
    //job.. 1
    //job.. 2

    println()

    launch {
        println("job2 1 : ${Thread.currentThread().name}")
        withContext(Dispatchers.Default) {
            println("job2 2 : ${Thread.currentThread().name}")
        }
    }
    //job2 1 : main
    //job2 2 : DefaultDispatcher-worker-1

    val job2 = launch {
        // 특정 시간동안만 움직여야하는 경우
        // withTimeout() 지정한 시간이 지나면, 내부적으로 exception 발생. 코루틴이 취소된다.
        withTimeout(1300) {
            repeat(100) {
                println("job3..$it")
                delay(500)
            }
        }
        println("step 1")   // withTimeout때문에 해당 구문은 출력되지 않는다.
    }
    job2.join()
    //job3..0
    //job3..1
    //job3..2


    // withTimeout을 사용하면서 시간이 지난 후 코루틴이 계속 움직이게 하고 싶다면
    val job3 = launch {
        // withTimeout() 지정한 시간이 지나면, 내부적으로 exception 발생 시키지 말고 null. 코루틴이 취소된다.
        withTimeoutOrNull(1300) {
            repeat(100) {
                println("job4..$it")
                delay(500)
            }
        }
        println("step 2")   // withTimeoutOrNull으로 사용했기에 해당 구문 출력됨
    }
    job2.join()
    //job4..0
    //job4..1
    //job4..2
    //step 2

    delay(2000)

}