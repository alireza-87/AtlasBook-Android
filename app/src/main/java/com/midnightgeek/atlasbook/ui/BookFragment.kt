package com.midnightgeek.atlasbook.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.midnightgeek.atlasbook.MainActivity
import com.midnightgeek.atlasbook.R
import com.midnightgeek.atlasbook.databinding.FragmentBookBinding
import com.midnightgeek.atlasbook.ui.callback.AdsDelegate
import com.midnightgeek.atlasbook.utils.AdsHandler
import com.midnightgeek.atlasbook.utils.StorageHandler
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import java.io.File
import javax.inject.Inject


class BookFragment : DaggerFragment() {

    lateinit var binder: FragmentBookBinding
    val TAG = "BookFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: BookFragmentViewModel

    @Inject
    lateinit var mInterstitialAd: AdsHandler

    @Inject
    lateinit var storageHandler: StorageHandler

    companion object {
        val TAG = "LoginFragment"
        fun newInstance() = BookFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_book, container, false)
        val view = binder.root
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(BookFragmentViewModel::class.java)
        binder.viewmodel = viewModel
        viewModel.initData(getBid())
        initObserve()
        initAds()
        return view
    }

    private fun getBid(): Int {
        return arguments!!.getInt("bid")
    }

    private fun initAds() {

    }

    private fun initObserve() {

        viewModel.share.observe(this, Observer {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share, it.name))
            ContextCompat.startActivity(context!!, Intent.createChooser(intent, "Share ... "), null)
        })

        viewModel.back.observe(this, Observer {
            if (it) {
                (activity as MainActivity).onBackPressed()
            }
        })

        viewModel.error.observe(this, Observer {
            when (it) {
                2 -> {
                    Toast.makeText(context!!, getString(R.string.error_download), Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

        viewModel.book.observe(this, Observer {
            binder.data = it
            binder.imgBook.setImageURI("https://atlasbook.ams3.digitaloceanspaces.com/cover/" + it.image)
            if (it.modelFavorite!=null){
                binder.imgFavorite.setImageResource(R.drawable.ic_fav_fill)
            }else{
                binder.imgFavorite.setImageResource(R.drawable.ic_fav_empty)
            }
        })

        viewModel.read.observe(this, Observer {
            mInterstitialAd.show(object : AdsDelegate {
                override fun afterAdsClose() {
                    if (it.modelFile != null) {
                        if (File(storageHandler.getBookPath() + "/" + it.modelFile!!.name).exists()) {
                            //storageHandler.openFile(storageHandler.getBookPath()+"/"+it.modelFile!!.name)
                            (activity as MainActivity).openBookReader(it.bid!!)
                            return
                        }
                    }
                    Toast.makeText(
                        context!!,
                        getString(R.string.error_file_notfound),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }, 50)
        })

        viewModel.download.observe(this, Observer {
            binder.clProgress.visibility = View.VISIBLE
            binder.btnDownload.visibility = View.GONE
            mInterstitialAd.show(null, 100)

        })

        viewModel.progress.observe(this, Observer {
            when {
                it == 100 -> {
                    binder.btnDownload.visibility = View.VISIBLE
                    binder.clProgress.visibility = View.GONE
                }
                it < 0 -> {
                    binder.btnDownload.visibility = View.VISIBLE
                    binder.clProgress.visibility = View.GONE
                }
                else -> {
                    binder.lblProgress.text = "$it%"
                    binder.progress.progress = it
                }
            }
        })
    }
}