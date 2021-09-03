package com.android.committeeapp.member

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.time.Year


@Dao
interface MembersDAO {

    @Insert
    suspend fun addMember(memberModel: MemberModel)

    @Delete
    suspend fun deleteMember(memberModel: MemberModel)

    @Query("Select * from member where c_fk_id = :committeeId  order by m_created_date desc")
    fun getMembers(committeeId: Int): LiveData<List<MemberModel>>

    @Insert
    suspend fun prePopulate(prefs: List<MemberModel>)

    @Insert
    suspend fun addMemberPay(memberPayModel: MemberPayModel)


    @Query("DELETE FROM member_pay WHERE c_fk_id = :committeeId and m_fk_id = :memberId and month = :month and year = :year and day=:day")
    suspend fun deleteMemberPay(committeeId: Int, memberId: Int, month: Int, day: Int, year: Int)



    @Query("Select * from member_pay where c_fk_id = :committeeId and m_fk_id = :memberId")
    fun getMemberPay(committeeId: Int, memberId: Int): LiveData<List<MemberPayModel>>
}