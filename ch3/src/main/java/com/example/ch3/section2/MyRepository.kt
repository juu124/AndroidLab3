package com.example.ch3.section2

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyRepository {
    fun job1(): Int {
        var sum = 0
        for (i in 1..10) {
            Thread.sleep(100)
            sum += i
        }
        return sum
    }

    fun job2(): Flow<Int> {
        return flow {
            var sum = 0
            for (i in 1..10) {
                Thread.sleep(100)
                sum += i
            }
            emit(sum)
        }
    }
}