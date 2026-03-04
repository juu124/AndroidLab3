package com.example.ch3.section2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

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

    // repository에 있는 로직을 메인 스레드로 돌리면 앱이 UI상으로 아무것도 못하는 현상이 나온다.
    // 그래서 Dispatchers를 활용해서 스레드를 변경한다.

    val repository = MyRepository()
    fun job1() {
        viewModelScope.launch(Dispatchers.Default) {
            // liveData로 묶으면 activity에서 observer가 실행된다.
            liveData.postValue(repository.job1())
        }
    }

    fun job2() : Flow<Int> {
        return repository.job2()
            .flowOn(Dispatchers.Default)
    }
}