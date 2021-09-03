package com.android.committeeapp.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.android.committeeapp.R
import com.android.committeeapp.comittee.CommitteeItemHolder
import com.android.committeeapp.comittee.CommitteeModel
import com.android.committeeapp.member.MemberItemHolder
import com.android.committeeapp.member.MemberModel

class HomeAdapter : ListAdapter<HomeItemModel, HomeItemHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            COMMITTEE_ITEM_TYPE -> CommitteeItemHolder(itemView)
            MEMBER_ITEM_TYPE -> MemberItemHolder(itemView)
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        val itemModel = getItem(position)
        when {
            (itemModel is MemberModel && holder is MemberItemHolder) -> holder.bind(itemModel)
            (itemModel is CommitteeModel && holder is CommitteeItemHolder) -> holder.bind(
                itemModel
            )

        }
    }

    override fun onViewRecycled(holder: HomeItemHolder) {
        super.onViewRecycled(holder)
        when (holder) {
            is MemberItemHolder -> holder.recycle()
            is CommitteeItemHolder -> holder.recycle()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CommitteeModel -> COMMITTEE_ITEM_TYPE
            is HomeItemModel -> MEMBER_ITEM_TYPE
            else -> super.getItemViewType(position)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<HomeItemModel>() {

        override fun areItemsTheSame(
            oldItem: HomeItemModel,
            newItem: HomeItemModel
        ): Boolean {
            return when {
                (oldItem is MemberModel && newItem is MemberModel) ->
                    oldItem.id == newItem.id
                (oldItem is CommitteeModel && newItem is CommitteeModel) ->
                    oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: HomeItemModel,
            newItem: HomeItemModel
        ): Boolean {
            return when {
                (oldItem is MemberModel && newItem is MemberModel) ->
                    oldItem.name == newItem.name &&
                            oldItem.turn == newItem.turn
                (oldItem is CommitteeModel && newItem is CommitteeModel) ->
                    oldItem.amount == newItem.amount && oldItem.name == newItem.name &&
                            oldItem.per_month_amount == newItem.per_month_amount
                else -> false
            }
        }
    }

    companion object {

        private const val COMMITTEE_ITEM_TYPE = R.layout.item_committee
        private const val MEMBER_ITEM_TYPE = R.layout.item_member
    }
}