package com.android.committeeapp.comittee

import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.android.committeeapp.common.HomeItemHolder
import com.android.committeeapp.member.MemberActivity
import kotlinx.android.synthetic.main.item_committee.view.*

class CommitteeItemHolder(itemView: View) : HomeItemHolder(itemView) {

    fun bind(model: CommitteeModel) {
        itemView.text_month.text = model.month
        itemView.text_day.text = model.day
        itemView.text_amount.text = "Rs ${model.amount}"
        itemView.text_title.text = model.name
        itemView.tv_duration.text = "${model.months} months"
        itemView.per_month.text = "Rs ${model.per_month_amount}"
        itemView.setOnClickListener {
            MemberActivity.start(itemView.context, model.id, model.name)
        }
    }

    fun recycle() {
        itemView.text_month.text = ""
        itemView.text_day.text = ""
        itemView.text_amount.text = ""
        itemView.text_title.text = ""
        itemView.setOnClickListener(null)
    }
}