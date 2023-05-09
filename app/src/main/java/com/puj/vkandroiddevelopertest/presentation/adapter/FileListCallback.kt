package com.puj.vkandroiddevelopertest.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.puj.vkandroiddevelopertest.domain.File

class FileListCallback(
    private val oldList: List<File>,
    private val newList: List<File>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}