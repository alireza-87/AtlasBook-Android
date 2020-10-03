package com.midnightgeek.atlasbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.midnightgeek.atlasbook.MainActivity
import com.midnightgeek.atlasbook.R
import com.midnightgeek.atlasbook.databinding.FragmentReaderBinding
import com.midnightgeek.atlasbook.utils.StorageHandler
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import java.io.File
import javax.inject.Inject

class ReaderFragment : DaggerFragment() {
    private val TAG = "ReaderFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var storageHandler: StorageHandler

    lateinit var binder: FragmentReaderBinding
    lateinit var viewModel: ReaderViewModel

    companion object {
        val TAG = "ReaderFragment"
        fun getInstance() = ReaderFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_reader, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ReaderViewModel::class.java)
        binder.viewmodel = viewModel
        viewModel.initData(getBid())
        initObserve()
        return binder.root
    }

    fun initObserve() {
        viewModel.file.observe(this, Observer {
            binder.pdfView.fromFile(File(storageHandler.getBookPath() + "/${it.name}")).load()
        })

        viewModel.back.observe(this, Observer {
            if (it) {
                (activity as MainActivity).onBackPressed()
            }
        })

    }

    fun getBid(): Int {
        return arguments!!.getInt("bid", 0)
    }


}