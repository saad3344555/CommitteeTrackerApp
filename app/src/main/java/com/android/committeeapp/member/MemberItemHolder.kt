package com.android.committeeapp.member

import android.view.View
import androidx.databinding.ViewDataBinding
import com.android.committeeapp.common.HomeItemHolder
import com.android.committeeapp.databinding.ItemMemberBinding
import kotlinx.android.synthetic.main.item_member.view.*

class MemberItemHolder(val binding: ItemMemberBinding) : HomeItemHolder(binding) {

    fun bind(model: MemberModel) {
        binding.model = model;
        binding.executePendingBindings()
        binding.root.setOnClickListener {
            MemberDetailActivity.start(
                itemView.context,
                model.c_id,
                model.id,
                model.name
            )
        }
    }


}