package com.android.committeeapp.comittee

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.android.committeeapp.common.HomeItemModel

@Entity(tableName = "committee")
data class CommitteeModel(
    @ColumnInfo(name = "c_name")
    var name: String,
    @ColumnInfo(name = "c_amount")
    var amount: Double,
    @ColumnInfo(name = "c_created_date")
    var createdDate: Long,
    @ColumnInfo(name = "c_month")
    var months: Int,
    @ColumnInfo(name = "c_per_month_amount")
    var per_month_amount: Double

) : HomeItemModel() {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "c_id")
    var id: Int = 0





}