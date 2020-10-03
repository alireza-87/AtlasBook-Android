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
import com.midnightgeek.atlasbook.databinding.ItemCategoryBinding
import com.midnightgeek.atlasbook.models.ModelCategory
import com.midnightgeek.atlasbook.ui.callback.ClickDelegate

class CategoryAdapter constructor(
    private val lifecycleOwner: LifecycleOwner?,
    private val callBack: ClickDelegate?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    constructor() : this(null, null)

    var data: ArrayList<ModelCategory> = ArrayList<ModelCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binder: ItemCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category,
            parent,
            false
        )
        return CategoryViewHolder(binder, lifecycleOwner!!)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(data[position], callBack)
    }

    class CategoryViewHolder(
        @NonNull val binding: ItemCategoryBinding,
        val lifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root), LifecycleOwner {
        private var binder: ItemCategoryBinding
        private val mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

        fun bind(modeCategory: ModelCategory, delegate: ClickDelegate?) {
            binder.data = modeCategory
            binder.delegate = delegate
            binder.executePendingBindings()

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

    fun update(updatedData: List<ModelCategory>) {
        data.clear()
        data.addAll(updatedData)
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        (holder as CategoryViewHolder).markAttach()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as CategoryViewHolder).markDetach()
    }

}