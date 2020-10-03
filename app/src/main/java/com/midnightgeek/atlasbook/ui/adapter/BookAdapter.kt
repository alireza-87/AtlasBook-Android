package com.midnightgeek.atlasbook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.midnightgeek.atlasbook.R
import com.midnightgeek.atlasbook.databinding.ItemBookBinding
import com.midnightgeek.atlasbook.models.ModelBook
import com.midnightgeek.atlasbook.ui.callback.ClickDelegate

class BookAdapter constructor(
    private val lifecycleOwner: LifecycleOwner?,
    private val callBack: ClickDelegate?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    constructor() : this(null, null)

    var data: ArrayList<ModelBook> = ArrayList<ModelBook>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binder: ItemBookBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_book,
            parent,
            false
        )
        return BookViewHolder(binder, lifecycleOwner!!)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BookViewHolder).bind(data[position], callBack)
    }

    class BookViewHolder(
        @NonNull val binding: ItemBookBinding,
        val lifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root), LifecycleOwner {
        private var binder: ItemBookBinding
        private val mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

        fun bind(modeBook: ModelBook, delegate: ClickDelegate?) {
            binder.data = modeBook
            binder.delegate = delegate
            binder.executePendingBindings()
            binder.imgBook.setImageURI("https://atlasbook.ams3.digitaloceanspaces.com/cover/" + modeBook.image)

        }

        fun markAttach() {
            mLifecycleRegistry.currentState = Lifecycle.State.STARTED
        }

        fun markDetach() {
            mLifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        }

        override fun getLifecycle(): Lifecycle {
            return mLifecycleRegistry
        }

        init {
            mLifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
            binder = binding
            binder.lifecycleOwner = lifecycleOwner
        }

    }

    fun update(updatedData: List<ModelBook>) {
        data.clear()
        data.addAll(updatedData)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        (holder as BookViewHolder).markAttach()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as BookViewHolder).markDetach()
    }

}