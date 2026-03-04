package com.example.ch3.section1

import android.util.Log
import androidx.lifecycle.ViewModel

class MyViewModel1 : ViewModel() {
    // 내부 작성규칙은 따로 없다.
    var data = "hello"
    fun someFun(): String {
        return "someFun result"
    }

    init {
        // 어떻게 생성되었고 소멸되었는지 테스트를 위해서 로그 추가
        // 화면 회전을 했을때 init 블럭이 또 호출이 될 것인지 보기 위해서 로그 추가
        Log.d("jay", "MyViewModel1 create...")
    }

    // lifecycle owner(activity 같은)을 참조하기는 하지만, 동일한 라이프사이클이 아니라
    // 주인이 소멸되는 시점을 참조해서 같이 소멸된다는 개념
    override fun onCleared() {
        super.onCleared()
        Log.d("jay", "MyViewModel1 onCleared...")
    }

    // activity의 상태 데이터로 가정
    var viewModelCount = 0
}