package com.midnightgeek.atlasbook.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.midnightgeek.atlasbook.models.ModelUser
import com.midnightgeek.atlasbook.repo.RepoHandler
import com.midnightgeek.atlasbook.utils.Authonticator
import com.midnightgeek.atlasbook.utils.PrefHandler
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class ProfileViewModel @Inject constructor(val repoHandler: RepoHandler,val authonticator: Authonticator,val prefHandler: PrefHandler) :ViewModel() {
    val user:MutableLiveData<ModelUser> = MutableLiveData()
    val click:MutableLiveData<Int> = MutableLiveData()
    val d= CompositeDisposable()
    private val executor: Executor = Executors.newSingleThreadExecutor()

    fun getUser(){
        user.value=authonticator.getUser()
    }

    fun onClick(data:Int){
        authonticator.signOut()
        prefHandler.clear()
        executor.execute {
            repoHandler.removeALl()
        }
        click.value=data
    }
}