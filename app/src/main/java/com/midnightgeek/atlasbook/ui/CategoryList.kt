package com.midnightgeek.atlasbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.midnightgeek.atlasbook.MainActivity
import com.midnightgeek.atlasbook.R
import com.midnightgeek.atlasbook.databinding.FragmentCategoryBinding
import com.midnightgeek.atlasbook.models.ModelCategory
import com.midnightgeek.atlasbook.ui.adapter.CategoryAdapter
import com.midnightgeek.atlasbook.ui.callback.ClickDelegate
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CategoryList: DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel:CategoryListViewModel
    lateinit var adapter:CategoryAdapter
    lateinit var binder:FragmentCategoryBinding
    var linearLayoutManager= LinearLayoutManager(activity)
    companion object {
        val TAG = "CategoryList"
        fun newInstance() = CategoryList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder=DataBindingUtil.inflate(inflater, R.layout.fragment_category,container,false)
        viewModel=ViewModelProviders.of(this,viewModelFactory).get(CategoryListViewModel::class.java)
        binder.viewModel=viewModel
        binder.lblPlaceHolder.visibility=View.VISIBLE
        initObserve()
        initAdapter()
        viewModel.getData()
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObserve(){
        viewModel.data.observe(this, Observer {
            if (it.isNotEmpty()){
                adapter.update(it)
                binder.lblPlaceHolder.visibility=View.GONE
            }else{
                binder.lblPlaceHolder.visibility=View.VISIBLE
            }
        })
    }

    private fun initAdapter(){
        adapter = CategoryAdapter(this, object : ClickDelegate {
            override fun onClick(data: Any) {
                (activity as MainActivity).openBookList((data as ModelCategory).cid!!)
            }
        })
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binder.rcvList.visibility = View.VISIBLE
        binder.rcvList.layoutManager = linearLayoutManager
        binder.rcvList.adapter = adapter

    }

}