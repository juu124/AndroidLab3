package com.example.ch1.section1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// vs thread ...
fun main() = runBlocking {
    // 리스트에 있는 데이터와 index를 활용하겠다.
    listOf("one", "two", "three").forEachIndexed { index, value ->
        // launch 사용 -> 코루틴을 구동
        // Dispatchers.Default는 다음 시간에 배움
        launch(Dispatchers.Default) {
            // 현 위치를 실행시키는 스레드의 이름을 찍었다.
            println("coroutine... $value start : ${Thread.currentThread().name}")
            // 대기 상태를 만들기 (IO, sleep) : 죽지는 않았지만 실행될 수 없는 상태
//            Thread.sleep(100L + index * 100L)

            // delay는 suspend function 함수이다.
            delay(100L + index * 100L)
            println("coroutine... $value end : ${Thread.currentThread().name}")
        }
    }

    // 테스트 되기 전에 프로세스가 종료되지 않게 하기 위해서
    // 메인 스레드를 죽지 않게 하기 위해서 추가한 것. 큰 의미는 없다.
    // 아래 코드 없으면 위에 일하고 바로 메인스레드도 종료시켜버리니까...
    Thread.sleep(2000)
}

// Thread.sleep(100L * index * 100L)을 사용한 경우 ===============
// start한 스레드와 end한 스레드가 동일한 스레드이다.
// ==> coroutine을 구동 시켰던 스레드가 blocking 된 것이다.
// 어 ? non-blocking이 아닌가? -> 지금 현재 Thread.sleep(100L + index * 100L)를 줘서 그렇다
//coroutine... two start : DefaultDispatcher-worker-2
//coroutine... three start : DefaultDispatcher-worker-3
//coroutine... one start : DefaultDispatcher-worker-1
//coroutine... one end : DefaultDispatcher-worker-1
//coroutine... two end : DefaultDispatcher-worker-2
//coroutine... three end : DefaultDispatcher-worker-3


// delay(100L + index * 100L)을 사용한 경우 ======================
// start한 스레드와 end한 스레드가 다른 스레드이다.
// 중단 상황(suspend function) 이 발생했을 때, thread가 대기하지(blocking) 않았다.
// 처음에는 3개가 필요했다. 중단사태가 발생했을 때 그냥 필요없다고 생각하고 1개를 지웠을 것이다.
// coroutine 을 이용했다고 해서 coroutine 을 구동시킨 스레드가 non-blocking이 되는 것이 아니라
// suspend 함수에서 동작을 해야 non-blocking이 된다.
//coroutine... two start : DefaultDispatcher-worker-2
//coroutine... one start : DefaultDispatcher-worker-1
//coroutine... three start : DefaultDispatcher-worker-3
//coroutine... one end : DefaultDispatcher-worker-2
//coroutine... two end : DefaultDispatcher-worker-2
//coroutine... three end : DefaultDispatcher-worker-2

// thread방식은 끝까지 3개를 유지했지만, coroutine의 suspend 함수들은 더 적은 스레드로 프로그램을 돌릴 수 있다.