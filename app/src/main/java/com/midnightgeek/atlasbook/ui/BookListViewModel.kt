package com.midnightgeek.atlasbook.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.midnightgeek.atlasbook.models.ModelBook
import com.midnightgeek.atlasbook.repo.RepoHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BookListViewModel @Inject constructor(var repo: RepoHandler) : ViewModel() {
    val bookList: MutableLiveData<List<ModelBook>> = MutableLiveData()
    val back:MutableLiveData<Boolean> = MutableLiveData()

    private val TAG = "BookListViewModel"
    private var disposable = CompositeDisposable()

    fun initData(catgId: Int) {
        disposable.add(
            repo.getCategoryBook(catgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "onSuccess -> ${it.size}")
                    bookList.value = it
                }
        )

    }

    fun initShelf() {
        disposable.add(
            repo.getShelfBook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "onSuccess -> ${it.size}")
                    bookList.value = it
                }
        )

    }

    fun initFavorite() {
        disposable.add(
            repo.getFavoriteBook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "onSuccess -> ${it.size}")
                    bookList.value = it
                }
        )

    }

    fun onback(){
        back.value=true
    }


}