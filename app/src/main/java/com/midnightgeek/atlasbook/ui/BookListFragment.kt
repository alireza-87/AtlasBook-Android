package com.midnightgeek.atlasbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.midnightgeek.atlasbook.MainActivity
import com.midnightgeek.atlasbook.R
import com.midnightgeek.atlasbook.databinding.FragmentBookListBinding
import com.midnightgeek.atlasbook.models.ModelBook
import com.midnightgeek.atlasbook.ui.adapter.BookAdapter
import com.midnightgeek.atlasbook.ui.callback.ClickDelegate
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BookListFragment : DaggerFragment() {
    lateinit var binder: FragmentBookListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: BookListViewModel
    val TAG = "BookListFragment"
    var adapterBookList = BookAdapter()
    var linearLayoutManager = GridLayoutManager(activity, 3)

    companion object {
        val TAG = "BookListFragment"
        fun getInstace() = BookListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_book_list, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BookListViewModel::class.java)
        when (getMode()) {
            1 -> {
                viewModel.initData(getCategoryId())
                binder.lblPlaceHolder.visibility=View.GONE
            }
            2 -> {
                viewModel.initShelf()
            }
            3->{
                viewModel.initFavorite()
            }
        }
        initAdapter()
        initObserve()
        binder.viewmodel=viewModel
        return binder.root
    }

    fun getCategoryId(): Int {
        return arguments!!.getInt("catg")
    }

    fun getMode(): Int {
        return arguments!!.getInt("mode")
    }


    fun initAdapter() {
        adapterBookList = BookAdapter(this, object : ClickDelegate {
            override fun onClick(data: Any) {
                (activity as MainActivity).openBookDetail((data as ModelBook).bid!!)
            }

        })
        binder.rcvList.visibility = View.VISIBLE
        binder.rcvList.layoutManager = linearLayoutManager
        binder.rcvList.adapter = adapterBookList

    }

    fun initObserve() {
        viewModel.bookList.observe(this, Observer {
            if (it.isNullOrEmpty())
                binder.lblPlaceHolder.visibility=View.VISIBLE
            else
                binder.lblPlaceHolder.visibility=View.GONE
            adapterBookList.update(it)
        })

        viewModel.back.observe(this, Observer {
            (activity as MainActivity).onBackPressed()
        })

    }

}