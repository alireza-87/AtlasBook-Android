package com.midnightgeek.atlasbook.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.midnightgeek.atlasbook.models.ModelFile
import com.midnightgeek.atlasbook.repo.RepoHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReaderViewModel @Inject constructor(var repoHandler: RepoHandler) : ViewModel() {
    val disposable = CompositeDisposable()
    val file: MutableLiveData<ModelFile> = MutableLiveData()
    val back: MutableLiveData<Boolean> = MutableLiveData()

    val TAG = "ReaderViewModel"
    fun initData(bid: Int) {
        disposable.add(
            repoHandler.getFile(bid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "onSuccess file")
                    file.value = it
                }
        )
    }

    fun onBack() {
        back.value = true
    }

}