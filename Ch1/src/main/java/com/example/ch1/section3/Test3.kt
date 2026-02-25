package com.example.ch1.section3

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// async - 코루틴 빌더 중의 하나.
// 코루틴의 작업하다가 발생한 데이터를 전달할 수 있다.
// 함수 단위 코루틴

// case 1 - 이미 준비된 scope를 이용한다.
// coroutine을 만들려면 scope가 있어야한다.
//fun one() = GlobalScope.async(Dispatchers.IO) {
//    println("coroutine one...")
//    delay(300)
//    "hello" // 결과
//}
//fun two() = GlobalScope.async(Dispatchers.Default) {
//    println("coroutine two...")
//    delay(200)
//    10 // 결과
//}
//fun main() = runBlocking{
//    println("main 1")
//    val oneDeferred = one()
//    println("main 2")
//    val twoDeferred = two()
//    println("main 3")
//    println("result : ${oneDeferred.await()} - ${twoDeferred.await()}")
//}


// fun String.a() {} 이렇게 원래 있던 함수처럼 확장하기

// case 2 - 함수를 scope의 확장함수로 등록하면 된다.
// 함수를 호출한 곳에서 이용하는 scope가 그대로 적용된다.
//fun CoroutineScope.one() = async(Dispatchers.IO) {
//    println("coroutine one...")
//    delay(300)
//    "hello" // 결과
//}
//fun CoroutineScope.two() = async(Dispatchers.Default) {
//    println("coroutine two...")
//    delay(200)
//    10 // 결과
//}

//fun main() = runBlocking{
//    println("main 1")
//    val oneDeferred = one()
//    println("main 2")
//    val twoDeferred = two()
//    println("main 3")
//    println("result : ${oneDeferred.await()} - ${twoDeferred.await()}")
//}

fun one(scope: CoroutineScope) = scope.async(Dispatchers.IO) {
    println("coroutine one...")
    delay(300)
    "hello" // 결과
}
fun two(scope: CoroutineScope) = scope.async(Dispatchers.Default) {
    println("coroutine two...")
    delay(200)
    10 // 결과
}

fun main() = runBlocking{
    println("main 1")
    val oneDeferred = one(CoroutineScope(Dispatchers.Default))
    println("main 2")
    val twoDeferred = two(CoroutineScope(Dispatchers.Default))
    println("main 3")
    println("result : ${oneDeferred.await()} - ${twoDeferred.await()}")
}

//main 1
//main 2
//coroutine one...
//main 3
//coroutine two...
//result : hello - 10