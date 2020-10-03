package com.midnightgeek.atlasbook.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.midnightgeek.atlasbook.models.ModelBook
import com.midnightgeek.atlasbook.models.ModelCategory
import com.midnightgeek.atlasbook.repo.RepoHandler
import com.midnightgeek.atlasbook.utils.StorageHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    var repo: RepoHandler,
    var storage: StorageHandler
) : ViewModel() {
    private val TAG = "MainFragmentViewModel"
    private var disposable = CompositeDisposable()
    val recentBook: MutableLiveData<List<ModelBook>> = MutableLiveData()
    val recomBook: MutableLiveData<List<ModelBook>> = MutableLiveData()

    fun initData() {
        Log.e(TAG, storage.getBookPath())

        disposable.add(
            repo.getRecentList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "getRecentList onSuccess -> ${it.size}")
                    recentBook.value = it
                }

        )

        disposable.add(
            repo.getRecomList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "getRecomList onSuccess recom -> ${it.size}")
                    recomBook.value = it
                }

        )

    }

    fun dispose() {
        disposable.clear()
    }
}