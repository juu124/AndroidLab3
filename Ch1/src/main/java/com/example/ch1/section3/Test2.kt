package com.example.ch1.section3

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// 코루틴 cancel
fun main() = runBlocking {
    println("main start...")

    // case 1 - 정상 cancel 된다
    // suspend 함수가 있었기 때문에 예외를 발생시켜서 정상 cancel 되었다.
//    val job = launch(Dispatchers.IO) {
//        var i = 0
//        while (i < 5) {
//            i++
//            delay(500)
//            println("coroutine : $i")
//        }
//    }

    //main start...
    //coroutine : 1
    //main cancel coroutine...
    //main end...

    // case 2 - cancel 명령이 내려졌다고 해서 코루틴이 cancel 되지 않는다.
    // 내부적으로 isActivity 프로퍼티 값을 참조해서 cancel 된것인지를 확인하고 내부에서 exception을 발생시켜
    // 종료시켜 줘야 한다.
    // coroutine 의 delay 같은 기초 함수들은 isActivity에 의한 exception 발생이 이미 준비되어 있다.
//    val job = launch(Dispatchers.IO) {
//        // 알고리즘 때문에 필요해서 시스템 시간을 가져왔다.
//        var start = System.currentTimeMillis()
//        var i = 0
//        while (i < 5) {
//            // delay 함수를 사용하기 싫어서 아래 알고리즘을 사용
//            if (System.currentTimeMillis() >= start) {
//                i++
//                println("coroutine : $i")
//                start += 500
//            }
//        }
//    }

    //main start...
    //coroutine : 1
    //coroutine : 2
    //coroutine : 3
    //main cancel coroutine...
    //coroutine : 4
    //coroutine : 5
    //main end...

    // case 3 - isActive 확인, exception 발생 ==> 정상 종료된다.
//    val job = launch(Dispatchers.IO) {
//        // 알고리즘 때문에 필요해서 시스템 시간을 가져왔다.
//        var start = System.currentTimeMillis()
//        var i = 0
//        while (i < 5) {
//            if (isActive) {
//                // delay 함수를 사용하기 싫어서 아래 알고리즘을 사용
//                if (System.currentTimeMillis() >= start) {
//                    i++
//                    println("coroutine : $i")
//                    start += 500
//                } else {
//                    throw CancellationException()
//                }
//            }
//        }
//    }

    //main start...
    //coroutine : 1
    //main cancel coroutine...
    //main end...

    // case 4 - 외부 요청에 의해 exception을 발생시켜서 종료시킨다.
    // 마지막 처리할 로직이 있다.
    // finally 에서 다시 exception이 발생하는 코드가 있을 수도 있다. delay같은..
    // 정상적으로 finally 가 모두 실행되지 못할 가능성도 있다.
    // 그래서
    val job = launch(Dispatchers.IO) {
        try {
            // 알고리즘 때문에 필요해서 시스템 시간을 가져왔다.
            var start = System.currentTimeMillis()
            var i = 0
            while (i < 5) {
                if (isActive) {
                    // delay 함수를 사용하기 싫어서 아래 알고리즘을 사용
                    if (System.currentTimeMillis() >= start) {
                        i++
                        println("coroutine : $i")
                        start += 500
                    }
                } else {
                    throw CancellationException()
                }
            }
        } finally {
            repeat(3) {
                println("coroutine finally.. $it")
                delay(100)      // cancel하면 여기에서 exception이 발생
            }
        }
    }

    //main start...
    //coroutine : 1
    //coroutine : 2
    //coroutine : 3
    //main cancel coroutine...
    //coroutine finally.. 0
    //main end...

    // case 5 - exception이 발생하는 부분이라고 하더라도 정상 실행을 하고 싶다면
//    val job = launch(Dispatchers.IO) {
//        try {
//            // 알고리즘 때문에 필요해서 시스템 시간을 가져왔다.
//            var start = System.currentTimeMillis()
//            var i = 0
//            while (i < 5) {
//                if (isActive) {
//                    // delay 함수를 사용하기 싫어서 아래 알고리즘을 사용
//                    if (System.currentTimeMillis() >= start) {
//                        i++
//                        println("coroutine : $i")
//                        start += 500
//                    } else {
//                        throw CancellationException()
//                    }
//                }
//            }
//        } finally {
//            // withContext 영역에서 실행하는 코드들은 exception을 발생하더라도 cancel 되지 않는다.
//            withContext(NonCancellable) {
//                repeat(3) {
//                    println("coroutine finally.. $it")
//                    delay(100)      // cancel하면 여기에서 exception이 발생
//                }
//            }
//        }
//    }

    //main start...
    //coroutine : 1
    //coroutine finally.. 0
    //coroutine finally.. 1
    //coroutine finally.. 2
    //main cancel coroutine...
    //main end...

    delay(1000)
    // 코루틴이 작업중이겠지만, 취소할거야~
    println("main cancel coroutine...")
    job.cancelAndJoin()
    println("main end...")
}