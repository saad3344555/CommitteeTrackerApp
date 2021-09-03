package com.android.committeeapp.comittee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.committeeapp.R
import com.android.committeeapp.common.AddFormActivity
import com.android.committeeapp.common.BaseActivity
import com.android.committeeapp.room.RoomDb
import com.android.committeeapp.common.HomeAdapter
import kotlinx.android.synthetic.main.activity_members.*
import kotlinx.android.synthetic.main.layout_committee.*
import kotlinx.coroutines.*

class CommitteeActivity : BaseActivity() {


    override var animationKind: Int = ANIMATION_SLIDE_FROM_RIGHT

    private lateinit var recyclerView: RecyclerView
    private lateinit var newExpenseButton: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var adapter: HomeAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private fun configureProgressBar(isVisible: Boolean) {
        progressBar.isVisible = isVisible
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter()
        layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    private fun bindViews() {
        newExpenseButton = findViewById(R.id.button_new_expense)
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun observeChanges() {
        RoomDb.get(this@CommitteeActivity).committeeDAO().getCommittee()
            .observe(this, Observer { ls ->
                adapter.submitList(ls)
                configureProgressBar(false)
            })
    }

    private fun setupNewExpenseLayout() {
        newExpenseButton.setOnClickListener { AddFormActivity.start(this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_committee)
        bindViews()
        setupNewExpenseLayout()
        setupRecyclerView()
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