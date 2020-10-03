package com.midnightgeek.atlasbook

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.midnightgeek.atlasbook.databinding.ActivityMainBinding
import com.midnightgeek.atlasbook.ui.*
import com.midnightgeek.atlasbook.utils.AdsHandler
import com.midnightgeek.atlasbook.utils.Authonticator
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    lateinit var binder: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var authonticator: Authonticator
    @Inject
    lateinit var mInterstitialAd: AdsHandler

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binder.viewmodel = viewModel
        if(authonticator.isLogin()){
            initMainPage()
        }else{
            openLoginFragment()
        }
        initNavBar()
        MobileAds.initialize(this) {
            Log.e("ADS", "done")
        }
        val adRequest: AdRequest = AdRequest.Builder().build()
        binder.adView.loadAd(adRequest)
        initStatusBarColor()
    }

    fun initNavBar() {
        binder.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.library -> {
                    initMainPage()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.shelf -> {
                    openShelf()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    openProfile()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    openFavorite()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.categoty -> {
                    openCategory()
                    return@setOnNavigationItemSelectedListener true
                }


                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    fun openLoginFragment() {
        binder.bottomNavigation.visibility = View.GONE

        supportFragmentManager
            .beginTransaction()
            .add(R.id.frlMain, LoginFragment.newInstance())
            .addToBackStack(LoginFragment.TAG)
            .commit()
    }

    fun initMainPage() {
        binder.bottomNavigation.visibility = View.VISIBLE

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frlMain, MainFragment.newInstance())
            .addToBackStack(MainFragment.TAG)
            .commit()

    }

    fun openBookDetail(bid: Int) {
        var bundle = Bundle()
        bundle.putInt("bid", bid)
        var fragment = BookFragment.newInstance()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frlMain, fragment)
            .addToBackStack(BookFragment.TAG)
            .commit()

    }

    fun openBookReader(bid: Int) {
        var bundle = Bundle()
        bundle.putInt("bid", bid)
        var fragment = ReaderFragment.getInstance()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frlMain, fragment)
            .addToBackStack(ReaderFragment.TAG)
            .commit()

    }


    fun openBookList(catgId: Int) {
        var bundle = Bundle()
        bundle.putInt("catg", catgId)
        bundle.putInt("mode", 1)
        var fragment = BookListFragment.getInstace()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frlMain, fragment)
            .addToBackStack(BookListFragment.TAG)
            .commit()

    }

    fun openProfile() {
        var fragment = ProfileFragment.getInstace()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frlMain, fragment)
            .addToBackStack(ProfileFragment.TAG)
            .commit()

    }

    fun openShelf() {
        var bundle = Bundle()
        bundle.putInt("mode", 2)
        var fragment = BookListFragment.getInstace()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frlMain, fragment)
            .addToBackStack(BookListFragment.TAG)
            .commit()

    }

    fun openFavorite() {
        var bundle = Bundle()
        bundle.putInt("mode", 3)
        var fragment = BookListFragment.getInstace()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frlMain, fragment)
            .addToBackStack(BookListFragment.TAG)
            .commit()

    }

    fun openCategory() {
        var fragment = CategoryList.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frlMain, fragment)
            .addToBackStack(CategoryList.TAG)
            .commit()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initStatusBarColor(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.statusBarColor = Color.BLACK
        }
    }

}