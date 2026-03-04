package com.example.ch3.section1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ch3.R
import com.example.ch3.databinding.ActivityTest1Binding

class Test1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityTest1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ViewModel 객체 준비
        // case 1 - 직접 생성
//        val viewModel: MyViewModel1 = MyViewModel1()

        // case 2 - ViewModelProvider 이용
        // ViewModelProvider 생성자 매개변수는 lifecycle owner.. 즉 뷰모델이 누구와 라이브사이클을 참조(소멸시점)한다.
        // 아래 코드는 activity가 onDestory하면 해당 viewModel의 생명주기도 거기까지다
//        val viewModel = ViewModelProvider(this).get(MyViewModel1::class.java)

        // case 3 - by delegator 이용 (ViewModelProvider의 축약형)
        // viewModels()는 activity에서 확장 함수로 지원해준다.
        // 가장 많이 사용하는 형태
        val viewModel: MyViewModel1 by viewModels()


        binding.button.setOnClickListener {
            // ViewModel 의 멤버(변수, 함수) 이용
            Toast.makeText(this, "${viewModel.data}, ${viewModel.someFun()}", Toast.LENGTH_SHORT)
                .show()
        }
    }
}