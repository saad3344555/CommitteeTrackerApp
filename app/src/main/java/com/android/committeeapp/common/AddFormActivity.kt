package com.android.committeeapp.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.android.committeeapp.R
import com.android.committeeapp.comittee.CommitteeModel
import com.android.committeeapp.member.MemberModel
import com.android.committeeapp.room.RoomDb
import kotlinx.android.synthetic.main.item_committee.*
import kotlinx.android.synthetic.main.layout_add_form.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AddFormActivity : BaseActivity() {


    override var animationKind = ANIMATION_SLIDE_FROM_BOTTOM

    var mForMembers = false;
    var mComId = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_form)
        setupActionBar()
        getExtra()
        configureViews()


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

        return if (mForMembers) {
            if (turn.text.isEmpty())
                turn.error = getString(R.string.required)
            if (editMemberTitle.text.isEmpty())
                editMemberTitle.error = getString(R.string.required)

            turn.text.isNotEmpty() && editMemberTitle.text.isNotEmpty()
        } else {
            if (perAmount.text.isEmpty())
                perAmount.error = getString(R.string.required)
            if (editTextAmount.text.isEmpty())
                editTextAmount.error = getString(R.string.required)
            if (editTextTitle.text.isEmpty())
                editTextTitle.error = getString(R.string.required)
            if (containerTags.text.isEmpty())
                containerTags.error = getString(R.string.required)

            perAmount.text.isNotEmpty() && editTextTitle.text.isNotEmpty() && containerTags.text.isNotEmpty() && editTextAmount.text.isNotEmpty()
        }
    }

    private fun save() {
        lifecycleScope.launch {
            RoomDb.get(this@AddFormActivity).let { rdb ->
                if (mForMembers) {
                    rdb.membersDAO().addMember(
                        MemberModel(
                            editMemberTitle.text.toString(),
                            turn.text.toString().toInt(),
                            Date().time,
                            mComId
                        )
                    )
                    this@AddFormActivity.finish()

                } else {
                    rdb.committeeDAO().addCommittee(
                        CommitteeModel(
                            editTextTitle.text.toString(),
                            editTextAmount.text.toString().toDouble(),
                            Date().time,
                            containerTags.text.toString().toInt(),
                            perAmount.text.toString().toDouble()
                        )
                    )

                    this@AddFormActivity.finish()

                }
            }

        }

    }

    fun getExtra() {
        mComId = intent?.extras?.getInt(COMMITTEE_ID) ?: -1
        mForMembers = mComId != -1

    }

    fun configureViews() {
        if (mForMembers) {
            turn.isVisible = true
            month.isVisible = true
            editMemberTitle.isVisible = true
            image_member.isVisible = true
        } else {
            perAmount.isVisible = true
            perSymbol.isVisible = true
            textSymbol.isVisible = true
            editTextAmount.isVisible = true

            imageTags.isVisible = true
            imageTitle.isVisible = true
            editTextTitle.isVisible = true
            containerTags.isVisible = true
        }
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