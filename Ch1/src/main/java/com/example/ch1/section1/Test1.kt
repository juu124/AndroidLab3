package com.example.ch1.section1

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

// 비동기를 목적으로 하는 코드를 작성하겠다.
fun main() = runBlocking() {
    println("main step 1")

    // 동기 - case 0
    // 시간이 오래 걸리는 작업이 있다고 가정
    // 아래의 코드는 동기적으로, main thread에 의해 실행되었다.
//    for (i in 1..10) {
//        println("main $i")
//        Thread.sleep(100)  // 0.1초씩 쉬면서 작업
//    }


    // 비동기(Thread) - case 1
    // Thread를 상속받아서 작성했다. 클래스 자체가 Thread가 된다.
    // Thread를 사용한 이유 : 시간이 오래걸리는 업무를 비동기적으로 처리하고 싶다는 뜻
    class MyThread : Thread() {
        override fun run() {
            // thread에 의해 비동기적으로 처리되어야 할 업무를 구현하는 곳이다.
            // 시간이 오래 걸리는 업무 구현
            for (i in 1..10) {
                println("Thread1 step $i")
                Thread.sleep(100)  // 0.1초씩 쉬면서 작업
            }
        }
    }
    // start하는 순간 스레드는 2개의 스레드가 만들어진다. (main Thread, MyThread)
    // start하고 바로 println("main step 2")을 실행했음
    MyThread().start()  // start해야 만들어진 개발자 스레드가 동작이된다.


    // 비동기(Thread) - case 2
    // Runnable 인터페이스를 구현하는 방식. 이것도 스레드 구현 방식중 하나
    // 위의 방식처럼 스레드를 상속받지 못하는 상황에서 사용하기도 한다.
    class MyThread2 : Runnable {
        override fun run() {
            for (i in 1..10) {
                println("Thread2 step $i")
                Thread.sleep(100)  // 0.1초씩 쉬면서 작업
            }
        }
    }
    // 구동하려면 스레드 자체를 상속 받은 것이 아니기 때문에
    // 스레드 객체를 생성하면서 해당 runnable을 매개변수로 넘겨야한다.
    Thread(MyThread2()).start()


    // 비동기(Thread) - case 3
    // 코틀린에서 제공하는 Thread 확장 api 방법 1
    // 위의 방식들과 같지만, 코틀린에서 Thread를 사용한다면, 아래 형식으로 많이 사용한다.
    // Thread { } 을 이용하는 경우에는 start() 명령을 내려야 구동된다.
    Thread {
        for (i in 1..10) {
            println("Thread3 step $i")
            Thread.sleep(100)  // 0.1초씩 쉬면서 작업
        }
    }.start()


    // 비동기(Thread) - case 4
    // 코틀린에서 제공하는 Thread 확장 api 방법 2
    // 별도의 start 명령을 내리지 않아도 thread가 만들어지고 바로 실행한다.
    thread {
        for (i in 1..10) {
            println("Thread4 step $i")
            Thread.sleep(100)  // 0.1초씩 쉬면서 작업
        }
    }



    // 비동기(coroutine) - case 1
    // launch : 코루틴의 가장 유명한 api
    // main 함수에 runBlocking() 을 추가
    val job = launch {
        for (i in 1..10) {
            println("coroutine.. $i")
            delay(100) // 0.1초씩 쉬면서 작업
        }
    }
    println("main step 2")
    job.join()
}