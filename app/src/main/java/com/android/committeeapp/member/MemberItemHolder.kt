package com.android.committeeapp.member

import android.view.View
import com.android.committeeapp.common.HomeItemHolder
import kotlinx.android.synthetic.main.item_member.view.*

class MemberItemHolder(itemView: View) : HomeItemHolder(itemView) {

    fun bind(model: MemberModel) {
        itemView.text_month.text = model.month
        itemView.text_day.text = model.day
        itemView.text_amount.text = model.turn.toString()
        itemView.text_title.text = model.name
        itemView.setOnClickListener {
            MemberDetailActivity.start(itemView.context,model.c_id,model.id,model.name)
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