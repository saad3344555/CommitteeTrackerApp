package com.android.committeeapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.committeeapp.comittee.CommitteeDao
import com.android.committeeapp.comittee.CommitteeModel
import com.android.committeeapp.member.MemberModel
import com.android.committeeapp.member.MemberPayModel
import com.android.committeeapp.member.MembersDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*
import kotlin.collections.ArrayList


@Database(
    entities = [MemberModel::class, CommitteeModel::class, MemberPayModel::class],
    version = 2,
    exportSchema = false
)
abstract class RoomDb : RoomDatabase() {

    abstract fun committeeDAO(): CommitteeDao
    abstract fun membersDAO(): MembersDAO

    companion object {
        @Volatile
        private var instance: RoomDb? = null
        private val LOCK = Any()

        fun get(context: Context) = instance ?: synchronized(LOCK) {
            buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            RoomDb::
            class.java, "committee-tracker.db"
        ).fallbackToDestructiveMigration().addCallback(rdc).build()

        var rdc: RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {

            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                // do something every time database is open
            }
        }
    }
}
