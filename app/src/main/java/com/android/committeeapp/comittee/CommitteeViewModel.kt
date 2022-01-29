package com.android.committeeapp.comittee

import android.content.Intent
import android.view.View
import androidx.lifecycle.*
import com.android.committeeapp.common.AddFormActivity
import com.android.committeeapp.common.AddViewModel
import com.android.committeeapp.room.RoomDb


class CommitteeViewModelFactory(private val roomDb: RoomDb) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommitteeViewModel(roomDb) as T
    }
}

class CommitteeViewModel(val roomDb: RoomDb) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>();


    fun fetchCommittees(): LiveData<List<CommitteeModel>> = roomDb.committeeDAO().getCommittee()


    fun navigate(view: View) {
        AddFormActivity.start(view.context);
    }
}