package com.android.committeeapp.member

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.android.committeeapp.comittee.CommitteeModel

@Entity(
    tableName = "member_pay", foreignKeys = arrayOf(
        ForeignKey(
            entity = CommitteeModel::class,
            parentColumns = arrayOf("c_id"),
            childColumns = arrayOf("c_fk_id")
        ), ForeignKey(
            entity = MemberModel::class,
            parentColumns = arrayOf("m_id"),
            childColumns = arrayOf("m_fk_id")
        )
    )
)
data class MemberPayModel(
    @ColumnInfo(name = "c_fk_id")
    val cfId: Int,

    @ColumnInfo(name = "m_fk_id")
    val mfId: Int,

    @ColumnInfo(name = "day")
    val day: Int,

    @ColumnInfo(name = "month")
    val month: Int,

    @ColumnInfo(name = "year")
    val year: Int

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mp_id")
    var memberPayId: Int = 0
}