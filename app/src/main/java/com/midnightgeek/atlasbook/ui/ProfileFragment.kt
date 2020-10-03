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
import com.midnightgeek.atlasbook.databinding.FragmentProfileBinding
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment:DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewmodel:ProfileViewModel
    lateinit var binder:FragmentProfileBinding
    companion object {
        val TAG = "ProfileFragment"
        fun getInstace() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binder=DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)
        viewmodel=ViewModelProviders.of(this,viewModelFactory).get(ProfileViewModel::class.java)
        binder.viewmodel=viewmodel
        initObserve()
        viewmodel.getUser()
        return binder.root
    }

    fun initObserve(){
        viewmodel.user.observe(this, Observer {
            binder.data=it
            binder.imgProfile.setImageURI(it.avatar)
        })

        viewmodel.click.observe(this, Observer {
            fragmentManager?.popBackStack()
            (activity as MainActivity).openLoginFragment()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}