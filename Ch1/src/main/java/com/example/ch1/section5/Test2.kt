package com.example.ch1.section5

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// buffer
// 발행과 구독이 속도차가 있는 경우
// 발행이 빠른 경우 미리 발행하고 싶다..
fun main() = runBlocking {
    // case 1 - buffer를 이용하지 않겠다.
    // 구독 준비가 되어야 다음 데이터를 발행할 수 있다.
//    val channel = Channel<Int>()

    //send 0
    //receive 0
    //receive 1
    //send 1
    //receive 2
    //send 2
    //receive 3
    //send 3
    //receive 4
    //send 4

    // case 2 - buffer를 사용한 경우
    // 버퍼를 사용하면 구독보다 발행이 더 빠르게 움직일 수가 있다.
    // 하지만, buffer가 full이 되면 그 다음부터는 구독의 속도에 맞춘다.
//    val channel = Channel<Int>(2)
    //send 0
    //receive 0
    //send 1       ==> 버퍼에 1
    //send 2       ==> 버퍼에 1, 2
    //receive 1    ==> 버퍼 2
    //send 3       ==> 버퍼 2, 3
    //receive 2
    //send 4
    //receive 3
    //receive 4


    // case 3 - buffer overflow 상황에서 drop oldest
    // 구독과 상관없이 발행속도대로 쭉 발행이 가능하다.
    // 발행한 모든 데이터가 구독에 전달되지 않을 수 있다..
//    val channel = Channel<Int>(2, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    //send 0
    //receive 0
    //send 1    ==>1
    //send 2    => 1,2
    //receive 1 ==> 2
    //send 3    => 2, 3
    //send 4    =>3, 4
    //receive 3
    //receive 4


    // case 4 - buffer overflow 상황에서 latest
    // buffer full 이후 발행 데이터가 구독에 전달되지 않는다.
    val channel = Channel<Int>(2, onBufferOverflow = BufferOverflow.DROP_LATEST)
    //send 0
    //receive 0
    //send 1    => 1
    //send 2    => 1, 2
    //receive 1 => 2
    //send 3    => 2, 3
    //send 4    => 2,3  가장 최신이 4이기 때문에 버퍼에 들어가지 않는다.
    //receive 2
    //receive 3

    launch {
        repeat(5) {
            channel.send(it)
            println("send $it")
            delay(100)
        }
        channel.close()
    }

    for (data in channel) {
        println("receive $data")
        delay(300)
    }
}