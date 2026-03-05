package com.example.ch3.section3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.ch3.R
import com.example.ch3.databinding.ActivityTest3Binding
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class Test3Activity : AppCompatActivity() {

    lateinit var dao: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityTest3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "test"
        )
            // room 기본으로 dbms 는 백그라운드 스레드에 의해 실행되어야 한다.
            // 만약 메인 스레드에서 실행시키려면 선언해야 한다.
            .allowMainThreadQueries()
            .addMigrations(migration)   // 스키마 수정
            .build()

        dao = db.userDao()
        binding.button5.setOnClickListener {
            // id를 0으로 다 주면 auto 적으로 된다
//            val user1 = User(0, "gildong1", "hong1")
//            val user2 = User(0, "gildong2", "hong2")
//            val user3 = User(0, "gildong3", "hong3")
//
//            // 내부적으로 mapping되고 sql 되는지 모른다. 그냥 insert만 하고 싶음
//            dao.insertUser(user1)
//            dao.insertUser(user2)
//            dao.insertUser(user3)
//
            var resultTxt = ""
//
//            dao.getAll().forEach {
//                resultTxt += "${it.firstName}, ${it.lastName}"
//            }
//            binding.textView.text = resultTxt

            // livedata
//            dao.getUserLiveData(1).observe(this) {
//                resultTxt = "1... ${it.firstName} ${it.lastName}"
//                binding.textView.text = resultTxt
//            }

            // flow
//            lifecycleScope.launch {
//                dao.getUserFlow()
//                    .collect {
//                        resultTxt = "2... ${it.firstName} ${it.lastName}"
//                        binding.textView.text = resultTxt
//                    }
//            }

//            val cursor = dao.getUserCursor(1)
//            if(cursor.moveToFirst()) {
//                binding.textView.text = "3 .. ${cursor.getString(1)}, ${cursor.getString(2)}"
//            }

            // migration 테스트 ================================
            val user = User(0, "gildong4", "hong4", "a@a.com")
            dao.insertUser(user)
            dao.getAll().forEach {
                resultTxt += "$it \n"
            }
            binding.textView.text = resultTxt
            // =================================================


        }
    }
}