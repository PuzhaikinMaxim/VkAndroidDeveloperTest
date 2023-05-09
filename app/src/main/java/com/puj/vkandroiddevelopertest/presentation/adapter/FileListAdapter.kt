package com.puj.vkandroiddevelopertest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.puj.vkandroiddevelopertest.R
import com.puj.vkandroiddevelopertest.databinding.ItemFileBinding
import com.puj.vkandroiddevelopertest.domain.File

class FileListAdapter(private var context: Context?): Adapter<FileListAdapter.ItemFileViewHolder>() {

    var fileList = listOf<File>()
        set(value) {
            val callback = FileListCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onItemClickListener: ((File) -> (Unit))? = null

    private val imageResourceIds = context!!.resources.obtainTypedArray(R.array.file_types)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFileViewHolder {
        val binding = ItemFileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemFileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemFileViewHolder, position: Int) {
        val item = fileList[position]
        with(holder.binding){
            tvFileName.text = item.fileName
            tvSize.text = context!!.getString(R.string.tv_size_text, item.size)
            tvCreationDate.text = context!!.getString(R.string.tv_creation_date_text, item.creationDate)
            ivFileType.setImageResource(imageResourceIds.getResourceId(item.fileType.ordinal,0))
            cvFile.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    class ItemFileViewHolder(val binding: ItemFileBinding): ViewHolder(binding.root)
}