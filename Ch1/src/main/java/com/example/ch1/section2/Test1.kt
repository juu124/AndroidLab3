package com.example.ch1.section2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

// scope를 이용한 여러 coroutine 구조화 한다.
class MyScope() : CoroutineScope {

    // coroutineContext : 이 scope 내에서 실행되는 coroutine 을 위한 공통 설정정보
    // Dispatchers.Default - 이 scope 에서 실행되는 모든 coroutine은 기본 default 스레드에서 동작한다.
    // 물론 개별 coroutine에서 Dispatcher를 바꿀 수 있기는 하지만..

    // 개별 coroutine의 Job 들은 scope Job의 하위가 된다.
    // scope Job으로 전체 제어가 가능하다.
    val scopeJob: Job = Job()
    override val coroutineContext: CoroutineContext
        // 사칙연산자라고 생각하기보다 연산자 재정의라고 생각하자
        // 필요한 만큼 설정 작업을 추가한다.
        get() = Dispatchers.Default + scopeJob
}

// runBlocking에 스코프가 지정되어있기 때문에 앞에서 사용했을 때 스코프 지정안해도 됐던것
// 우리는 Android에서 더 많이 사용할테니까 범위지정해야됨
// 하나의 범위안에 2개의 코루틴이 만들어졌다.
fun main() = runBlocking {
    val myScope = MyScope() // scope 선언
    // 그 scope 내에 coroutine을 만들고 실행
    val job1: Job= myScope.launch {    // Job은 개별 코루틴을 지징한다. (업무1개)
        repeat(3) {
            delay(300)
            println("first coroutine $it")
        }
    }

    val job2: Job= myScope.launch {    // Job은 개별 코루틴을 지징한다. (업무1개)
        repeat(3) {
            delay(200)
            println("second coroutine $it")
        }
    }

    delay(500)
    // 코루틴 취소나 종료를 한다면
    // 개별 coroutine 제어
//    job1.cancel()
//    job2.cancel()

    // scope라는 개념이 여러 코루틴 구조화한 개념이다.
    // 이 scope에 의해 실행되는 모든 코루틴 업무 취소
    myScope.scopeJob.cancel()

    delay(500)
}
// job1.cancel()을 호출했을 때=========================
//second coroutine 0
//first coroutine 0
//second coroutine 1
//second coroutine 2

// job2.cancel()을 호출했을 때=========================
//second coroutine 0
//first coroutine 0
//second coroutine 1
//first coroutine 1
//first coroutine 2

// myScope.scopeJob.cancel()을 호출했을 때==============
//second coroutine 0
//first coroutine 0
//second coroutine 1