package com.android.committeeapp.member

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.android.committeeapp.comittee.CommitteeModel
import com.android.committeeapp.common.HomeItemModel

@Entity(
    tableName = "member", foreignKeys = arrayOf(
        ForeignKey(
            entity = CommitteeModel::class,
            parentColumns = arrayOf("c_id"),
            childColumns = arrayOf("c_fk_id"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class MemberModel(
    @ColumnInfo(name = "m_name")
    var name: String,
    @ColumnInfo(name = "m_turn")
    var turn: Int,
    @ColumnInfo(name = "m_created_date")
    var createdDate: Long,
    @ColumnInfo(name = "c_fk_id")
    var c_id: Int
) : HomeItemModel() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "m_id")
    var id: Int = 0
}