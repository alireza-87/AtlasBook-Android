package com.midnightgeek.atlasbook.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.midnightgeek.atlasbook.models.ModelBook
import com.midnightgeek.atlasbook.repo.RepoHandler
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelBookDb
import com.midnightgeek.atlasbook.utils.Mapper
import com.midnightgeek.atlasbook.utils.StorageHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class BookFragmentViewModel @Inject constructor(
    var repo: RepoHandler,
    var storageHandler: StorageHandler
) : ViewModel() {
    private val TAG = "BookFragmentViewModel"
    private var disposable = CompositeDisposable()
    val book: MutableLiveData<ModelBook> = MutableLiveData()
    val download: MutableLiveData<ModelBook> = MutableLiveData()
    val share: MutableLiveData<ModelBook> = MutableLiveData()
    val read: MutableLiveData<ModelBook> = MutableLiveData()
    val progress: MutableLiveData<Int> = MutableLiveData()
    val error: MutableLiveData<Int> = MutableLiveData()
    val back: MutableLiveData<Boolean> = MutableLiveData()
    val remove: MutableLiveData<ModelBook> = MutableLiveData()

    private var downloadId: Int = 1
    fun initData(bid: Int) {
        disposable.add(
            repo.getBook(bid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "onSuccess ->")
                    book.value = it
                }
        )
    }

    fun onDownload(data: ModelBook) {
        download.value = data
        download(data)
    }

    fun onRemove(data: ModelBook) {
        remove(data)
    }

    fun onRead(data: ModelBook) {
        read.value = data
    }

    fun onShare(data: ModelBook) {
        share.value = data

    }

    fun onBack() {
        back.value = true
    }

    fun onCancel() {
        FileDownloader.getImpl().pause(downloadId)

    }

    fun onFavorite(data:ModelBook){
        if (data.modelFavorite!=null){
            disposable.add(
                repo.removeFromFavorite(data.bid!!).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.e(TAG, "onSuccess ->")
                        storageHandler.deleteFile(storageHandler.getBookPath() + "/${data.name}")
                        remove.value = data
                    }
            )

        }else{
            disposable.add(
                repo.addToFavorite(data.bid!!).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.e(TAG, "onSuccess ->")
                        storageHandler.deleteFile(storageHandler.getBookPath() + "/${data.name}")
                        remove.value = data
                    }
            )

        }
    }

    private fun remove(data: ModelBook) {
        disposable.add(
            repo.deleteFile(data.bid!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "onSuccess ->")
                    storageHandler.deleteFile(storageHandler.getBookPath() + "/${data.name}")
                    remove.value = data
                }
        )
    }

    private fun download(data: ModelBook) {
        val dTask =
            FileDownloader.getImpl().create("https://www.atlas-shelf.me/download?bid=" + data.bid)
                .setPath(storageHandler.getBookPath() + "/" + storageHandler.generateFileName(data.sFileName!!))
                .setListener(object : FileDownloadListener() {
                    override fun pending(
                        task: BaseDownloadTask,
                        soFarBytes: Int,
                        totalBytes: Int
                    ) {
                        Log.e(TAG, "pending")
                    }

                    override fun started(task: BaseDownloadTask) {
                        Log.e(TAG, "started")
                    }

                    override fun connected(
                        task: BaseDownloadTask,
                        etag: String,
                        isContinue: Boolean,
                        soFarBytes: Int,
                        totalBytes: Int
                    ) {
                        Log.e(TAG, "connect")
                    }

                    override fun progress(
                        task: BaseDownloadTask,
                        soFarBytes: Int,
                        totalBytes: Int
                    ) {
                        val p = ((soFarBytes * 100)) / totalBytes
                        Log.e(TAG, "progress : $p")
                        progress.value = p
                    }

                    override fun blockComplete(task: BaseDownloadTask) {
                        Log.e(TAG, "blockComplete")
                    }

                    override fun retry(
                        task: BaseDownloadTask,
                        ex: Throwable,
                        retryingTimes: Int,
                        soFarBytes: Int
                    ) {
                        Log.e(TAG, "retry")
                    }

                    override fun completed(task: BaseDownloadTask) {
                        Log.e(TAG, "complete")
                        progress.value = 100
                        val mapper = Mapper()
                        var modelDb = mapper.map(data, ModelBookDb::class.java) as ModelBookDb
                        disposable.add(
                            repo.updateLocalFileName(
                                modelDb,
                                storageHandler.generateFileName(data.sFileName!!)
                            )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    Log.e(TAG, "onSuccess ->")
                                }
                        )
                    }

                    override fun paused(
                        task: BaseDownloadTask,
                        soFarBytes: Int,
                        totalBytes: Int
                    ) {
                        Log.e(TAG, "pause")
                        progress.value = -1
                    }

                    override fun error(
                        task: BaseDownloadTask,
                        e: Throwable
                    ) {
                        Log.e(TAG, "error $e")
                        progress.value = -2
                    }

                    override fun warn(task: BaseDownloadTask) {
                        Log.e(TAG, "warnn")
                    }
                })
        downloadId = dTask.id
        dTask.start()
    }


}