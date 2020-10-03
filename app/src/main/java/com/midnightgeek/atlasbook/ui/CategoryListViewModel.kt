package com.midnightgeek.atlasbook.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.midnightgeek.atlasbook.models.ModelCategory
import com.midnightgeek.atlasbook.repo.RepoHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoryListViewModel @Inject constructor(val repoHandler: RepoHandler) :ViewModel() {
    val TAG = "CategoryListViewModel"

    val data:MutableLiveData<List<ModelCategory>> = MutableLiveData()
    val click:MutableLiveData<ModelCategory> = MutableLiveData()
    private var disposable = CompositeDisposable()

    fun getData(){
        disposable.add(
            repoHandler.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, " getCategory onSuccess -> ${it.size}")
                    data.value = it
                }

        )

    }

    fun onClick(data:ModelCategory){
        click.value=data
    }
}