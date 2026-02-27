package com.example.ch2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ch2.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding

    // 상황에 따라 호출되어 화면을 조정하는 함수
    fun changeVisibility(mode: String) {
        if (mode === "login") {
            binding.run {
                authMainTextView.text = "${MyApplication.email} 님 반갑습니다."
                logoutBtn.visibility = View.VISIBLE
                goSignUpBtn.visibility = View.GONE
                authEmailEditView.visibility = View.GONE
                authPasswordEditView.visibility = View.GONE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.GONE
            }

        } else if (mode === "logout") {
            binding.run {
                authMainTextView.text = "로그인 하거나 회원가입 해주세요."
                logoutBtn.visibility = View.GONE
                goSignUpBtn.visibility = View.VISIBLE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
            }
        } else if (mode === "signup") {
            binding.run {
                logoutBtn.visibility = View.GONE
                goSignUpBtn.visibility = View.GONE
                authEmailEditView.visibility = View.VISIBLE
                authPasswordEditView.visibility = View.VISIBLE
                signBtn.visibility = View.VISIBLE
                loginBtn.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)

        if (MyApplication.checkAuth()) {
            // 로그인 되어 있는 상황
            changeVisibility("login")
        } else {
            // 로그인 되어 있지 않은 상황
            changeVisibility("logout")
        }

        binding.logoutBtn.setOnClickListener {
            // 로그 아웃 처리
            MyApplication.auth.signOut()
            MyApplication.email = null
            changeVisibility("logout")
        }

        binding.goSignUpBtn.setOnClickListener {
            // 회원가입 버튼이 클릭되면
            changeVisibility("signup")
        }

        binding.signBtn.setOnClickListener {
            // 회원가입
            // 유저 입력 데이터 획득
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()

            // firebase 에 저장
            MyApplication.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    // 유저 입력 데이터.. 화면에서 삭제..
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    // 성공상황에서..
                    if (task.isSuccessful) {
                        // 검증 이메일 발송해줘..
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { sendTask ->
                                if (sendTask.isSuccessful) {
                                    // 이메일 검증 성공 상황
                                    Toast.makeText(
                                        this@AuthActivity,
                                        "회원가입 성공. 전송된 이메일 확인",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    changeVisibility("logout")
                                } else {
                                    // 이메일 검증 실패 상황
                                    Toast.makeText(
                                        this@AuthActivity,
                                        "메일 전송 실패",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    changeVisibility("logout")
                                }
                            }
                    } else {
                        Toast.makeText(this@AuthActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        changeVisibility("logout")
                    }
                }
        }

        // 로그인 버튼 클릭...
        binding.loginBtn.setOnClickListener {
            // 유저 입력 데이터 획득
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()

            MyApplication.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    // 유저 입력 데이터.. 화면에서 삭제..
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    // 성공상황에서..
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            // 다 성공됨
                            MyApplication.email = email
                            changeVisibility("login")
                        } else {
                            Toast.makeText(
                                this@AuthActivity,
                                "전송된 메일로 이메일 인증이 되지 않았습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@AuthActivity,
                            "로그인 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}