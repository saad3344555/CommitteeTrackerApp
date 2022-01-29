package com.android.committeeapp.comittee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.committeeapp.R
import com.android.committeeapp.common.*
import com.android.committeeapp.room.RoomDb
import com.android.committeeapp.databinding.ActivityAddFormBinding
import com.android.committeeapp.databinding.ActivityCommitteeBinding
import kotlinx.android.synthetic.main.activity_members.*
import kotlinx.android.synthetic.main.item_member.*
import kotlinx.android.synthetic.main.layout_committee.*
import kotlinx.coroutines.*

class CommitteeActivity : BaseActivity() {


    override var animationKind: Int = ANIMATION_SLIDE_FROM_RIGHT
    lateinit var binding: ActivityCommitteeBinding


    private val viewModel: CommitteeViewModel by viewModels<CommitteeViewModel> {
        CommitteeViewModelFactory(RoomDb.get(this@CommitteeActivity))
    }

    private fun configureProgressBar(isVisible: Boolean) {
        viewModel.isLoading.value = false;
    }
//
//    private fun setupRecyclerView() {
//        adapter = HomeAdapter()
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = layoutManager
//    }



    private fun observeChanges() {

        viewModel.fetchCommittees().observe(this, Observer { ls ->
            (binding.fragmentHome.recyclerView.adapter as HomeAdapter).submitList(ls)
            configureProgressBar(false)
        }
        )

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_committee)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_committee)
        binding.lifecycleOwner = this@CommitteeActivity
        binding.fragmentHome.recyclerView.adapter = HomeAdapter()

        setupToolbar()
        observeChanges()

    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setTitle(R.string.committee)
    }


    companion object {

        fun start(context: Context) {
            val intent = Intent(context, CommitteeActivity::class.java)
            context.startActivity(intent)
        }
    }

}