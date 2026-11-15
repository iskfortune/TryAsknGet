package com.example.tryasknget.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tryasknget.room.InquiryDao
import com.example.tryasknget.room.OfferDao
import com.example.tryasknget.room.RecommendationDao
import com.example.tryasknget.room.UserDao
import com.example.tryasknget.room.Category
import com.example.tryasknget.room.Inquiry
import com.example.tryasknget.room.Offer
import com.example.tryasknget.room.Recommendation
import com.example.tryasknget.room.User

@Database(
    entities = [User::class, Inquiry::class, Recommendation::class, Offer::class, Category::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun inquiryDao(): InquiryDao
    abstract fun recommendationDao(): RecommendationDao
    abstract fun offerDao(): OfferDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "asknget_room_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}