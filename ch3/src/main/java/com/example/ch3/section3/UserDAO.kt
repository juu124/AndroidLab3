package com.example.ch3.section3

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// DAO - Data Access Object
// db 작업시 호출할 함수를 가지는 클래스
@Dao
interface UserDAO {
    @Query("SELECT * FROM User")
    fun getAll() : List<User>

    @Insert
    fun insertUser(users: User)

    // LiveData로 결과 받을 수 있다.
    // SQL 문 동적 가능하다 (:id)
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserLiveData(id: Int) : LiveData<User>

    @Query("SELECT * FROM user")
    fun getUserFlow(): Flow<User>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserCursor(id: Int): Cursor


    // Converter 테스트 ==============================
    @Insert
    fun insertCustomer(customer: Customer)

    @Query("SELECT * FROM Customer")
    fun getAllCustomer(): List<Customer>
}