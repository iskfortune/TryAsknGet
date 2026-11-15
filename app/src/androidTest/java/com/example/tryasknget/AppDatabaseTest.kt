package com.example.tryasknget

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tryasknget.room.UserDao
import com.example.tryasknget.room.AppDatabase
import com.example.tryasknget.room.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        if (::db.isInitialized){
            db.close()
        }

    }

    @Test
    fun testInsertAndSelectUser() = runBlocking {
        val user = User(
            fullName = "Fortune Isika",
            email = "fortune@strathmore.edu",
            passwordHash = "password123",
            trustScore = 5.0
        )

        userDao.registerUser(user)

        val fetchedUser = userDao.loginUser("fortune@strathmore.edu", "password123")
        assertNotNull(fetchedUser)
        assertEquals("Fortune Isika", fetchedUser?.fullName)
        assertEquals("fortune@strathmore.edu", fetchedUser?.email)
    }

    @Test
    fun testUpdateUser() = runBlocking {
        val user = User(
            fullName = "Jane Doe",
            email = "jane@strathmore.edu",
            passwordHash = "secret",
            trustScore = 4.0
        )
        userDao.registerUser(user)
        val savedUser = userDao.loginUser("jane@strathmore.edu", "secret")!!

        val updatedUser = savedUser.copy(trustScore = 4.8)
        userDao.updateUser(updatedUser)

        val result = userDao.loginUser("jane@strathmore.edu", "secret")
        assertNotNull(result)
        assertEquals(4.8, result?.trustScore ?: 0.0, 0.01)
    }
}