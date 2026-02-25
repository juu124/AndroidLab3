package com.example.ch1.section3

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// builder 의 start 옵션
fun main() = runBlocking {
    // 자동으로 start. start 되기전에 cancel되었고 구동되지 않는다.
    val job1 = launch {
        println("job1...")
    }
    job1.cancel()

    // job2..1 이렇게 로그가 남았다.
    // 자동 start 한뒤 cancel 명령이 떨어져도 start
    // delay때문에 예외를 발생해서 job2...2가 찍히지 않았다.
    val job2 = launch(start = CoroutineStart.ATOMIC) {
        println("job2...1")
        delay(100)
        println("job2...2")
    }
    job2.cancel()


    // CoroutineStart.UNDISPATCHED로 지정을 하면 코루틴 시작은 코루틴을 만든 thread에서 start 되라..
    // 그런데, 실행되다가 suspend 상황이 발생이 되고, 다시 해제되었을 때는 context에서 지정한 thread에서 동작하라(Dispatchers.IO)
    val job3 = launch(start = CoroutineStart.UNDISPATCHED, context = Dispatchers.IO) {
        println("job3..1 ${Thread.currentThread().name}")
        delay(200)
        println("job3..2 ${Thread.currentThread().name}")
    }
    job3.join()
    //job3..1 main
    //job3..2 DefaultDispatcher-worker-1


    val job4 = launch(start = CoroutineStart.LAZY) {
        println("job4....")
    }
    delay(200)
    println("main...")
    job4.start()
    job4.join()
    //main...
    //job4....
}