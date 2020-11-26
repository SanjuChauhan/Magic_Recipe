package com.magic.recipe.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magic.recipe.R
import com.magic.recipe.databinding.ItemDataBinding
import com.magic.recipe.model.RecipePuppy
import java.util.*

class RecipePuppyRvAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: MutableList<RecipePuppy> = ArrayList()
    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemDataBinding>(
            LayoutInflater.from(context),
            R.layout.item_data, parent, false
        )
        return PeopleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as PeopleViewHolder
        viewHolder.setData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun add(data: RecipePuppy) {
        dataList.add(data)
        notifyItemInserted(dataList.size - 1)
    }

    fun addAtPosition(position: Int, data: RecipePuppy) {
        dataList.add(position, data)
        notifyItemInserted(position)
    }

    fun addAll(dataList: List<RecipePuppy>) {
        dataList.forEach { data ->
            add(data)
        }
    }

    private fun remove(data: RecipePuppy) {
        val position = dataList.indexOf(data)
        if (position > -1) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): RecipePuppy {
        return dataList[position]
    }

    fun setItemAtPosition(position: Int, data: RecipePuppy) {
        dataList[position] = data
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        remove(dataList[position])
    }

    fun getFactsRowsList(): List<RecipePuppy>? {
        return dataList
    }

    fun setPeopleList(dataList: MutableList<RecipePuppy>) {
        this.dataList = dataList
    }

    fun size(): Int {
        return dataList.size
    }

    internal inner class PeopleViewHolder(var itemBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setData(data: RecipePuppy) {

            Glide.with(context)
                .load(data.thumbnail)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(itemBinding.ivImage)

            itemBinding.data = data

            itemBinding.rlRootLayout.setOnClickListener {
                mOnItemClickListener?.onItemClick(data, adapterPosition)
            }
        }
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(data: RecipePuppy, position: Int)
    }
}
