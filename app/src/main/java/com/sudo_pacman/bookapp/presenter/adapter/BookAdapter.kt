package com.sudo_pacman.bookapp.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sudo_pacman.bookapp.data.BookData
import com.sudo_pacman.bookapp.databinding.ItemBookBinding
import com.sudo_pacman.bookapp.utils.myLog

class BookAdapter : ListAdapter<BookData, BookAdapter.BookViewHolder>(BookDiffUtil) {

    private var onClickItem: ((BookData) -> Unit)? = null
    private var onLongClickItem: ((BookData) -> Unit)? = null
    private var onClickDownload: ((BookData) -> Unit)? = null

    object BookDiffUtil : DiffUtil.ItemCallback<BookData>() {
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean = oldItem.orderName == newItem.orderName

        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean = oldItem == newItem
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) : ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickItem?.invoke(getItem(adapterPosition))
            }
        }

        fun bind() {

            "adapter book ${getItem(adapterPosition)}".myLog()

            binding.bookName.text = getItem(adapterPosition).bookName
            binding.bookAuthor.text = getItem(adapterPosition).authorName
            binding.bookImg.setImageBitmap(getItem(adapterPosition).image)


            binding.btnDownload.setOnClickListener {
                onClickDownload?.invoke(getItem(adapterPosition))
            }

//            binding.animationView.setOnClickListener {
//                val animationView = binding.animationView
//                animationView.setAnimation("splash_animation.json")
//                animationView.playAnimation()
//            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder = BookViewHolder(
        ItemBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) = holder.bind()

    fun setOnClickItem(block: (BookData) -> Unit) {
        onClickItem = block
    }

    fun setOnLongClickItem(block: (BookData) -> Unit) {
        onLongClickItem = block
    }

    fun setOnDownloadClick(block: (BookData) -> Unit) {
        onClickDownload = block
    }
}