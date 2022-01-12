package com.android.committeeapp.member

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.committeeapp.R
import com.android.committeeapp.common.BaseActivity
import com.android.committeeapp.common.HomeAdapter
import com.android.committeeapp.room.RoomDb
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import kotlinx.android.synthetic.main.layout_member_detail.*
import kotlinx.android.synthetic.main.layout_members.*
import kotlinx.coroutines.launch
import java.util.*

class MemberDetailActivity
    : BaseActivity() {

    private var comId: Int = 0
    private var memId: Int = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var newExpenseButton: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var adapter: HomeAdapter
    private lateinit var layoutManager: LinearLayoutManager


    private fun getExtras() {
        comId = intent?.extras?.getInt(
            COMMITTEE_ID
        ) ?: 0

        memId = intent?.extras?.getInt(
            MEMBER_ID
        ) ?: 0

    }

    private fun deleteSelected(): Boolean {
        MaterialAlertDialogBuilder(this)
            .setMessage(R.string.delete_member_message)
            .setPositiveButton(R.string.yes) { _, _ -> delete() }
            .setNegativeButton(R.string.no) { _, _ -> }
            .show()
        return true
    }

    private fun setupCalendar() {

        val date = Date()
        val cal = Calendar.getInstance()
        cal.time = date
        val month = cal[Calendar.MONTH]
        val year = cal[Calendar.YEAR]
        calendar_view
        calendar_view.state().edit()
            .setMinimumDate(
                CalendarDay.from(
                    year - 2,
                    month+1,
                    1
                )
            )
            .setMaximumDate(CalendarDay.from(year + 2, month+1, 1))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit();

        calendar_view.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                lifecycleScope.launch {
                    RoomDb.get(this@MemberDetailActivity).membersDAO()
                        .addMemberPay(MemberPayModel(comId, memId, date.day, date.month, date.year))
                    Log.d("cal", " Selected ${date.day} ${date.month} ${date.year}")
                }
            } else {

                lifecycleScope.launch {

                    RoomDb.get(this@MemberDetailActivity).membersDAO()
                        .deleteMemberPay(comId, memId, date.month, date.day, date.year)
                    Log.d("cal", "Not Selected ${date.day} ${date.month} ${date.year}")
                }

            }
        }

    }

    private fun observeChanges() {
        RoomDb.get(this@MemberDetailActivity).membersDAO().getMemberPay(
            comId, memId
        ).observe(this,
            Observer<List<MemberPayModel>> { ls ->

                lifecycleScope.launch {
                    ls.forEach { mp ->
                        calendar_view.setDateSelected(
                            CalendarDay.from(
                                mp.year,
                                mp.month,
                                mp.day
                            ), true
                        )
                    }
                }

            }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_detail)
        getExtras()
        setupCalendar()
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
                this@MemberDetailActivity.finish()
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
            RoomDb.get(this@MemberDetailActivity).membersDAO()
                .deleteMember(MemberModel("", 0, 0, 0).apply {
                    id = memId
                })

            this@MemberDetailActivity.finish()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title =
            intent?.extras?.getString(
                MEMBER_NAME
            ) ?: ""

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)

    }


    companion object {

        private const val COMMITTEE_ID: String = "committee_id"
        private const val MEMBER_ID: String = "member_id"
        private const val MEMBER_NAME: String = "committee_name"

        fun start(context: Context, committee_id: Int, member_id: Int, committee_name: String) {
            val intent = Intent(context, MemberDetailActivity::class.java)
            intent.putExtra(COMMITTEE_ID, committee_id)
            intent.putExtra(MEMBER_ID, member_id)
            intent.putExtra(MEMBER_NAME, committee_name)
            context.startActivity(intent)
        }
    }
}
