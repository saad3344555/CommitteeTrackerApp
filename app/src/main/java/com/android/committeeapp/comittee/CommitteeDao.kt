package com.android.committeeapp.comittee

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.committeeapp.member.MembersDAO


@Dao
interface CommitteeDao {

    @Insert
    suspend fun addCommittee(committeeModel: CommitteeModel)

    @Delete
    suspend fun deleteCommittee(committeeModel: CommitteeModel)

    @Query("Select * from committee order by c_created_date desc")
    fun getCommittee(): LiveData<List<CommitteeModel>>

    @Insert
    suspend fun prePopulate(prefs: List<CommitteeModel>)
}