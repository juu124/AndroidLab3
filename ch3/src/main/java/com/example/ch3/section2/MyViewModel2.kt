package com.example.ch3.section2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel2 : ViewModel() {
    // B/L 에 의해서 발생하는 데이터라고 가정하기
    // private 으로 처리해서 외부에서 직접 사용 못한다.
    private var count = 0

    // 여러 업무가 진행된다면 여러개 준비해도 된다.
    val liveData = MutableLiveData<Int>()

    // activity에서 B/L을 위해 호출하는 함수라고 가정하기
    fun changeCount() {
        count++
        // 데이터 발행하기
        liveData.postValue(count)
    }
}