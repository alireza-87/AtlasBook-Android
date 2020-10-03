package com.midnightgeek.atlasbook.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.midnightgeek.atlasbook.MainActivity
import com.midnightgeek.atlasbook.R
import com.midnightgeek.atlasbook.databinding.FragmentMainBinding
import com.midnightgeek.atlasbook.models.ModelBook
import com.midnightgeek.atlasbook.models.ModelCategory
import com.midnightgeek.atlasbook.ui.adapter.BookAdapter
import com.midnightgeek.atlasbook.ui.adapter.CategoryAdapter
import com.midnightgeek.atlasbook.ui.callback.ClickDelegate
import com.midnightgeek.atlasbook.utils.AdsHandler
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MainFragment : DaggerFragment() {
    private val TAG = "MainFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mInterstitialAd: AdsHandler

    lateinit var viewModel: MainFragmentViewModel
    lateinit var binder: FragmentMainBinding

    private val linearLayoutManagerRecentBook = LinearLayoutManager(activity)
    private val linearLayoutManagerRecomBook = LinearLayoutManager(activity)

    private lateinit var adapterRecentBook: BookAdapter
    private lateinit var adapterRecomBook: BookAdapter

    companion object {
        val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val view = binder.root
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainFragmentViewModel::class.java)
        binder.viewmodel = viewModel
        viewModel.initData()
        initObserve()
        initAdapter()
        return view
    }


    private fun initAdapter() {
        adapterRecentBook = BookAdapter(this, object : ClickDelegate {
            override fun onClick(data: Any) {
                (activity as MainActivity).openBookDetail((data as ModelBook).bid!!)
            }

        })

        adapterRecomBook = BookAdapter(this, object : ClickDelegate {
            override fun onClick(data: Any) {
                (activity as MainActivity).openBookDetail((data as ModelBook).bid!!)
            }

        })


        linearLayoutManagerRecentBook.orientation = LinearLayoutManager.HORIZONTAL
        binder.rcvRecent.visibility = View.VISIBLE
        binder.rcvRecent.layoutManager = linearLayoutManagerRecentBook
        binder.rcvRecent.adapter = adapterRecentBook

        linearLayoutManagerRecomBook.orientation = LinearLayoutManager.HORIZONTAL
        binder.rcvRecomm.visibility = View.VISIBLE
        binder.rcvRecomm.layoutManager = linearLayoutManagerRecomBook
        binder.rcvRecomm.adapter = adapterRecomBook

    }

    fun initObserve() {

        viewModel.recentBook.observe(this, Observer {
            if (it.isNotEmpty())
                adapterRecentBook.update(it)
        })

        viewModel.recomBook.observe(this, Observer {
            Log.e("OPS ", it.size.toString())
            if (it.isNotEmpty())
                adapterRecomBook.update(it)
        })

    }
}