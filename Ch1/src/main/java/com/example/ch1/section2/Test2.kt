package com.example.ch1.section2

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class MyScope2 : CoroutineScope {
    val scopeJob = Job()        // super Job

    // scope 내에서 실행되는 여러 coroutine의 공통 예외 처리
    val exeptionHandler = CoroutineExceptionHandler { context, throwable ->
        println("Coroutine Exception : ${context[CoroutineName.Key]} : $throwable")
    }
    override val coroutineContext: CoroutineContext
        get() = CoroutineName("my-scope") + Dispatchers.Default + scopeJob + exeptionHandler
}

fun main() = runBlocking {
    val scope = MyScope2()
    scope.launch {
        println("${coroutineContext[CoroutineName.Key]} is execute on thread ${Thread.currentThread().name}")
        delay(300)
        throw Exception("error... coroutine1...")
    }

    // scope에서 실행되는 coroutine은 기본 scope의 context 정보대로 움직이지만,
    // 원한다면 개발 coroutine에서 context 정보 교체가 가능하다.
    // launch에 ()를 추가하고 넣으면된다.
    //
    scope.launch(CoroutineName("my-coroutine") + newSingleThreadContext("one thread")) {
        println("${coroutineContext[CoroutineName.Key]} is execute on thread ${Thread.currentThread().name}")
    }

    delay(1000)

}

//CoroutineName(my-scope) is execute on thread DefaultDispatcher-worker-2
//CoroutineName(my-coroutine) is execute on thread one thread
//Coroutine Exception : CoroutineName(my-scope) : java.lang.Exception: error... coroutine1...