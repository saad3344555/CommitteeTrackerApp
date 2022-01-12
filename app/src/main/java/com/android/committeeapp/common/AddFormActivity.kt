package com.android.committeeapp.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.committeeapp.R
import com.android.committeeapp.comittee.CommitteeModel
import com.android.committeeapp.databinding.ActivityAddFormBinding
import com.android.committeeapp.databinding.LayoutAddFormBinding
import com.android.committeeapp.member.MemberModel
import com.android.committeeapp.room.RoomDb
import kotlinx.android.synthetic.main.item_committee.*
import kotlinx.android.synthetic.main.layout_add_form.*
import kotlinx.coroutines.launch
import java.util.*


class AddFormActivity : BaseActivity() {


    lateinit var binding: ActivityAddFormBinding
    override var animationKind = ANIMATION_SLIDE_FROM_BOTTOM

    private val viewModel: AddViewModel by viewModels<AddViewModel> {
        AddViewModelFactory(RoomDb.get(this@AddFormActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_form)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_form)
        binding.lifecycleOwner = this@AddFormActivity
        binding.fragmentNavigationHost.lifecycleOwner = this
        binding.fragmentNavigationHost.viewModel = viewModel
        binding.viewModel = viewModel
        getExtra()
        setupActionBar()
    }


    private fun setupActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_clear_24dp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator: MenuInflater = menuInflater
        inflator.inflate(R.menu.menu_new_expense, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this@AddFormActivity.finish()
                return true
            }
            R.id.save -> {
                if (validate()) {
                    save()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validate(): Boolean {
        return viewModel.validateFields(getString(R.string.required))
    }

    private fun save() {
        lifecycleScope.launch {
            RoomDb.get(this@AddFormActivity).let { rdb ->
                viewModel.save().apply {
                    this@AddFormActivity.finish()
                }
            }

        }
    }

    fun getExtra() {
        viewModel.mComId = intent?.extras?.getInt(COMMITTEE_ID) ?: -1
        viewModel.isMember.value = viewModel.mComId != -1

    }

    companion object {

        private const val COMMITTEE_ID = "com_id"

        fun start(context: Context, committee_id: Int = -1) {
            val intent = Intent(context, AddFormActivity::class.java).apply {
                putExtra(COMMITTEE_ID, committee_id)
            }
            context.startActivity(intent)
        }

    }
}