package com.android.committeeapp.common

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.committeeapp.R
import com.android.committeeapp.comittee.CommitteeModel
import com.android.committeeapp.member.MemberModel
import com.android.committeeapp.room.RoomDb
import kotlinx.android.synthetic.main.layout_add_form.*
import java.util.*


class AddViewModelFactory(private val roomDb: RoomDb) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddViewModel(roomDb) as T
    }
}

class AddViewModel(private val roomDb: RoomDb) : ViewModel() {
    var isMember = MutableLiveData<Boolean>()

    var turns = MutableLiveData<String>();
    var memberTitle = MutableLiveData<String>();
    var comitteeTitle = MutableLiveData<String>();
    var months = MutableLiveData<String>();
    var perMonthAmount = MutableLiveData<String>();
    var totalAmount = MutableLiveData<String>();


    var turnsError = MutableLiveData<String?>();
    var memberTitleError = MutableLiveData<String?>();
    var comitteeTitleError = MutableLiveData<String?>();
    var monthsError = MutableLiveData<String?>();
    var perMonthAmountError = MutableLiveData<String?>();
    var totalAmountError = MutableLiveData<String?>();

    var mComId = -1;

    init {
        isMember.value = false
    }

    suspend fun save(): Unit {
        if (isMember.value == true) {
            return roomDb.membersDAO().addMember(
                MemberModel(
                    memberTitle.value.toString(),
                    turns.value.toString().toInt(),
                    Date().time,
                    mComId
                )
            )
        } else {
           return roomDb.committeeDAO().addCommittee(
                CommitteeModel(
                    comitteeTitle.value.toString(),
                    totalAmount.value.toString().toDouble(),
                    Date().time,
                    months.value.toString().toInt(),
                    perMonthAmount.value.toString().toDouble()
                )
            )

        }
    }

    fun validateFields(errMsg: String): Boolean {
        return if (isMember.value == true) {
            if (turns.value.isNullOrEmpty())
                turnsError.value = errMsg
            if (memberTitle.value.isNullOrEmpty())
                memberTitleError.value = errMsg

            return turns.value.isNullOrEmpty().not() && memberTitle.value.isNullOrEmpty().not()
        } else {
            if (perMonthAmount.value.isNullOrEmpty())
                perMonthAmountError.value = errMsg
            if (comitteeTitle.value.isNullOrEmpty())
                comitteeTitleError.value = errMsg
            if (totalAmount.value.isNullOrEmpty())
                totalAmountError.value = errMsg
            if (monthsError.value.isNullOrEmpty())
                monthsError.value = errMsg

            perMonthAmount.value.isNullOrEmpty().not() && comitteeTitle.value.isNullOrEmpty()
                .not() && monthsError.value.isNullOrEmpty().not() && months.value.isNullOrEmpty()
                .not()
        }
    }

}