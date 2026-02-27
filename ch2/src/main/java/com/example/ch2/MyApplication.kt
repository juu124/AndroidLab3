package com.example.ch2

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyApplication : Application() {
    // 마치 static 처럼 이용가능
    companion object {
        val auth: FirebaseAuth by lazy {
            Firebase.auth
        }

        // 로그인 성공시에 로그인 id, 앱 전역에서 사용해서
        var email: String? = null
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                if (currentUser.isEmailVerified) {
                    true
                } else {
                    false
                }
            } ?: let {
                false
            }
        }
    }
}