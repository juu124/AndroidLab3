package com.example.ch1.section1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 개발자 함수를 suspend function으로 만들어서 활용하기
// 코루틴으로 처리되어야 하는 업무 단위 함수를 suspend function으로 만들 수도 있고
// 일반 함수로 만들 수도 있다.
// 함수 내부에서 중단상황(thread blocking)이 발생하게 되면, 일반 함수는 thread blocking 된다.
// suspend function은 thread non-blocking
// 그래서 가급적으로 코루틴에 의해 처리되어야 하는 업무는 그 엄무 내부에서 중단상황이 발생하든, 발생하지 않든, suspend funaction으로 선언하는 것이 유리하다.

// suspend 함수
suspend fun suspendFun(no: Int) : Int {
    var sum = 0
    for (i in 1..no) {
        delay(i * 10L)
        sum += i
    }
    // suspend 함수 내에서 일반 함수 호출 가능하다
    noSuspendFun(0)
    return  sum
}

// 테스트를 위한 일반 함수도 추가
fun noSuspendFun(no: Int) : Int{
    // 일반 함수 내에서 suspend 함수는 호출할 수 없다.
    // 그래서 main함수에서도 그냥 suspend함수를 사용할 수 없다.
//    suspendFun(10)  // error
    return 10
}

// 일반 함수 내에서 suspend 함수는 호출할 수 없다.
// 그래서 main함수에서도 그냥 suspend함수를 사용할 수 없다.
// 그렇기 때문에 runBlocking을 사용한다.
fun main() = runBlocking{
    for (i in 1..3) {
        launch(Dispatchers.Default) {
            println("coroutine.. $i start : ${Thread.currentThread().name}")
//            suspendFun(10)
            noSuspendFun(10)
            println("coroutine.. $i end : ${Thread.currentThread().name}")
        }
    }
}

// suspendFun(10) 이렇게 호출했을 때의 결과값
// 스레드를 3개를 사용했지만 start 스레드와 end 스레드가 다르다
//coroutine.. 1 start : DefaultDispatcher-worker-1
//coroutine.. 2 start : DefaultDispatcher-worker-2
//coroutine.. 3 start : DefaultDispatcher-worker-3
//coroutine.. 3 end : DefaultDispatcher-worker-1
//coroutine.. 2 end : DefaultDispatcher-worker-2
//coroutine.. 1 end : DefaultDispatcher-worker-3

// noSuspendFun(10) 이렇게 호출했을 때의 결과값
//coroutine.. 2 start : DefaultDispatcher-worker-2
//coroutine.. 3 start : DefaultDispatcher-worker-3
//coroutine.. 1 start : DefaultDispatcher-worker-1
//coroutine.. 2 end : DefaultDispatcher-worker-2
//coroutine.. 3 end : DefaultDispatcher-worker-3
//coroutine.. 1 end : DefaultDispatcher-worker-1