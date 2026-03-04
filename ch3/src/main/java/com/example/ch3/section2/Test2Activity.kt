package com.example.ch3.section2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.ch3.R
import com.example.ch3.databinding.ActivityTest2Binding

class Test2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 뷰 모델 준비
        val viewModel: MyViewModel2 by viewModels()

        // 일을 시키기 전에 옵저버 등록 : 결과 데이터를 받아내기 위해서
        viewModel.liveData.observe(this, object: Observer<Int> {
            // liveData에 데이터가 담기는 순간마다 실행된다.
            override fun onChanged(value: Int) {
                Log.d("jay", "count : $value")
            }
        })

        binding.button3.setOnClickListener {
            viewModel.changeCount()
        }
    }
}