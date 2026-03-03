package com.example.ch2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// FCM 으로 부터 토근, 메시지를 받기 위한 서비스
// 시스템에서 실행시킨다.
class MyFirebaseMessageService : FirebaseMessagingService() {
    // 앱을 install 하자마자 토큰을 전달할 목적으로 자동 호출된다.
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // 발급된 토근이 필요한 곳은 백앤드이다.
        // 백엔드에서 네트워킹
        // 이번 실습셍서 토큰이 정상적으로 됐는지만 확인해보자
        Log.d("jay", "fcm token : $token")
        // fhAiSoQ7Ro-TfVXzL4udv_:APA91bHjTatYQRn-lmnSQQ5Dprz0a9cM_2agMupmrI79Pi4hr85nK3xoA45_W7f0XoRcMBPw5kcJZoWd5P9QaeLU8EmtBX6T5KOVDAHX3XJC7WMREKLNP8w
    }

    // 백앤드가 전달한 데이터(메시지)가 도착할 때마다 호출된다.
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("jay", "fcm message : ${message.data}")
    }
}