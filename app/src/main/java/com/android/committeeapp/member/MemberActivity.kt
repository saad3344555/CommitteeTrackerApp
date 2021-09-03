package com.android.committeeapp.member

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.committeeapp.R
import com.android.committeeapp.comittee.CommitteeModel
import com.android.committeeapp.common.AddFormActivity
import com.android.committeeapp.common.BaseActivity
import com.android.committeeapp.room.RoomDb
import com.android.committeeapp.common.HomeAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.layout_members.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MemberActivity : BaseActivity() {


    private var comId: Int = 0
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
        val layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }


    private fun getExtras() {
        comId = intent?.extras?.getInt(
            COMMITTEE_ID
        ) ?: 0
    }


    private fun deleteSelected(): Boolean {
        MaterialAlertDialogBuilder(this)
            .setMessage(R.string.delete_committee_message)
            .setPositiveButton(R.string.yes) { _, _ -> delete() }
            .setNegativeButton(R.string.no) { _, _ -> }
            .show()
        return true
    }


    private fun bindViews() {
        progressBar = progress_bar
        recyclerView = recycler_view
        newExpenseButton = button_new_expense
    }

    private fun observeChanges() {

        RoomDb.get(this@MemberActivity).membersDAO().getMembers(
            comId
        ).observe(this, object : Observer<List<MemberModel>> {
            override fun onChanged(t: List<MemberModel>?) {
                adapter.submitList(t)

            }
        })
        configureProgressBar(false)
    }

    private fun setupNewExpenseLayout() {
        newExpenseButton.setOnClickListener {
            AddFormActivity.start(
                this, comId
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
        getExtras()
        bindViews()
        setupNewExpenseLayout()
        setupRecyclerView()
        setupToolbar()
        observeChanges()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_expense_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this@MemberActivity.finish()
                return true
            }
            R.id.delete -> {
                deleteSelected()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun delete() {
        lifecycleScope.launch {
            RoomDb.get(this@MemberActivity).committeeDAO()
                .deleteCommittee(CommitteeModel("", 0.0, 0, 0, 0.0).apply {
                    id = comId
                })

            this@MemberActivity.finish()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title =
            intent?.extras?.getString(
                COMMITTEE_NAME
            ) ?: ""

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)

    }


    companion object {

        private const val COMMITTEE_ID: String = "committee_id"
        private const val COMMITTEE_NAME: String = "committee_name"

        fun start(context: Context, committee_id: Int, committee_name: String) {
            val intent = Intent(context, MemberActivity::class.java)
            intent.putExtra(COMMITTEE_ID, committee_id)
            intent.putExtra(COMMITTEE_NAME, committee_name)

            context.startActivity(intent)
        }
    }
}