package com.android.committeeapp.comittee

import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.android.committeeapp.common.HomeItemHolder
import com.android.committeeapp.databinding.ItemCommitteeBinding
import com.android.committeeapp.member.MemberActivity
import kotlinx.android.synthetic.main.item_committee.view.*

class CommitteeItemHolder(val binding: ItemCommitteeBinding) : HomeItemHolder(binding) {

    fun bind(model: CommitteeModel) {
        binding.model = model
        binding.executePendingBindings()
        binding.root.setOnClickListener {  MemberActivity.start(itemView.context, model.id, model.name) }
    }
}