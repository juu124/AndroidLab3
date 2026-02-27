package com.example.ch1.section5

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// channel 기초
fun main() = runBlocking {
    // Channel을 준비하고 생상자와 소비자가 동일 Channel을 이용하면 된다.
    val channel = Channel<Int>()

    launch {
        repeat(3) {
            delay(100)
            // 발생한 데이터를 전달하고 싶다.
            channel.send(it)
            println("send $it")
        }
        channel.close()
    }

    // channel 데이터 획득
    // case 1 - receive() 한번만.. 반복적으로 받고 싶으면 repeat을 사용. receive가 반복 실행되게
    // 하지만 몇번 send할지 모를 수도 있음 그래서 repeat을 사용하기 불편할 수 있을 수도 있음
//    repeat(3) {
//        delay(300)
//        println("receive ${channel.receive()}")
//    }

    // send하니 receive했다는 것만 알아두기
    // 호출순서가 receive가 빨랐다고 해서 이상하게 생각하지 말기
    // 그냥 receive의 출력문이 빠른 것뿐임
    //receive 0
    //send 0
    //receive 1
    //send 1
    //receive 2
    //send 2

    // case 2 - for loop, channel이 close 될때까지
//    for (data in channel) {
//        println("receive $data")
//    }

    // case 3 -
    channel.consumeEach {
        println("receive $it")
    }

    delay(200)
}