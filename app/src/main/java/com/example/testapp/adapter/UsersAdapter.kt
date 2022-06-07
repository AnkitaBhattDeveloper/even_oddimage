package com.example.testapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.databinding.ItemLayoutBinding
import com.example.testapp.databinding.MainItemLayoutBinding
import com.example.testapp.databinding.OddItemLayoutBinding

import com.example.testapp.response.User
import kotlinx.android.synthetic.main.odd_item_layout.view.*

class UsersAdapter(val context: Context, val list: ArrayList<User>) :
    RecyclerView.Adapter<UsersAdapter.MainViewHolder>() {

    companion object {
        const val VIEW_EVEN_TYPE = 0
        const val VIEW_ODD_TYPE = 1
        const val SPAN_COUNT_ONE = 1
        const val SPAN_COUNT_TWO = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.MainViewHolder {
        val mainItemBinding =
            MainItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(mainItemBinding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val text = list[position].name
        val image = list[position].image


        Glide.with(context).load(image).into(holder.mainItemBinding.itemImage)
        holder.mainItemBinding.username.text = text
        val itemsAdapter = ItemsAdapter(list[position].items)

        val itemLayoutManager = GridLayoutManager(context, 2)
        itemLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p: Int): Int {
                return if ((list[holder.adapterPosition].items.size) % 2 == 0) {
                    SPAN_COUNT_ONE
                } else {
                    return if (p == 0)
                        SPAN_COUNT_TWO
                    else
                        SPAN_COUNT_ONE
                }
            }
        }

        holder.mainItemBinding.itemsRV.apply {
            adapter = itemsAdapter
            layoutManager = itemLayoutManager
            hasFixedSize()
        }

    }


    inner class ItemsAdapter(private val itemList: ArrayList<String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemViewType(position: Int): Int {
            return if (itemList.size % 2 == 0) {
                VIEW_EVEN_TYPE
            } else {
                return if (position == 0)
                    VIEW_ODD_TYPE
                else
                    VIEW_EVEN_TYPE

            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == VIEW_EVEN_TYPE) {
                val evenBinding =
                    ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EvenViewHolder(evenBinding)
            } else {
                val oddBinding =
                    OddItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                OddViewHolder(oddBinding)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            Glide.with(context).load(itemList[position]).into(holder.itemView.itemImage)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }


        inner class OddViewHolder(oddBinding: OddItemLayoutBinding) :
            RecyclerView.ViewHolder(oddBinding.root)

        inner class EvenViewHolder(evenBinding: ItemLayoutBinding) :
            RecyclerView.ViewHolder(evenBinding.root)
    }


    override fun getItemCount(): Int {
        return list.size
    }


    inner class MainViewHolder(val mainItemBinding: MainItemLayoutBinding) :
        RecyclerView.ViewHolder(mainItemBinding.root) {

    }


}