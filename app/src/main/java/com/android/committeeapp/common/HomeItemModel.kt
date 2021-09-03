package com.android.committeeapp.common

import androidx.room.Ignore
import com.android.committeeapp.comittee.CommitteeModel
import com.android.committeeapp.member.MemberModel
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

open class HomeItemModel {
    val day: String
        get() {
            return "dd".let {
                return@let SimpleDateFormat(it).format(
                    Date(
                        when (this) {
                            is MemberModel -> this.createdDate
                            is CommitteeModel -> this.createdDate
                            else -> throw Exception()
                        }
                    )
                )
            }
        }

    val month: String
        get() {
            return "MMM".let {
                return@let SimpleDateFormat(it).format(
                    Date(
                        when (this) {
                            is MemberModel -> this.createdDate
                            is CommitteeModel -> this.createdDate
                            else -> throw Exception()
                        }
                    )
                )
            }
        }

}