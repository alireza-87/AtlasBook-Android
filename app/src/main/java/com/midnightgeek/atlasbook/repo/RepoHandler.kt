package com.midnightgeek.atlasbook.repo

import android.util.Log
import com.midnightgeek.atlasbook.models.*
import com.midnightgeek.atlasbook.repo.local.LocalDataSource
import com.midnightgeek.atlasbook.repo.local.models.entity.*
import com.midnightgeek.atlasbook.repo.remote.ApiHandler
import com.midnightgeek.atlasbook.utils.Mapper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoHandler @Inject constructor(
    private var localDataSource: LocalDataSource,
    private var apiHandler: ApiHandler,
    private var mapper: Mapper
) {
    private val TAG = "RepoHandler"
    fun getCategory(): Observable<List<ModelCategory>> {
        return Observable.concatArrayEager(
            localDataSource.getAllCategory().subscribeOn(Schedulers.io()).map {
                var data = arrayListOf<ModelCategory>()
                it.forEach { modelapi ->
                    var a = mapper.map(modelapi, ModelCategory::class.java) as ModelCategory
                    data.add(a)
                }
                return@map data
            },
            Observable.defer {
                apiHandler.getCategoryList().subscribeOn(Schedulers.io()).map {
                    var data = arrayListOf<ModelCategoryDb>()
                    it.forEach { modelapi ->
                        var a = mapper.map(modelapi, ModelCategoryDb::class.java) as ModelCategoryDb
                        data.add(a)
                    }
                    localDataSource.saveCategory(data)

                    var dataCore = arrayListOf<ModelCategory>()
                    it.forEach { modelapi ->
                        var a = mapper.map(modelapi, ModelCategory::class.java) as ModelCategory
                        dataCore.add(a)
                    }
                    return@map dataCore

                }
            }
        )
    }

    fun getRecentList(): Observable<List<ModelBook>> {
        return Observable.concatArrayEager(
            localDataSource.getAllRecent().subscribeOn(Schedulers.io()).flatMap { its ->
                var ids = arrayListOf<Int>()
                its.forEach { item ->
                    item.bookId?.let { ids.add(it) }
                }
                localDataSource.getBooksByIds(ids).subscribeOn(Schedulers.io()).map {
                    var dataCore = arrayListOf<ModelBook>()
                    it.forEach { modeldb ->
                        var a = mapper.map(modeldb.modelBookDb, ModelBook::class.java) as ModelBook
                        if (modeldb.modelAutherDb != null) {
                            a.auther = mapper.map(
                                modeldb.modelAutherDb!!,
                                ModelAuther::class.java
                            ) as ModelAuther
                        }

                        if (modeldb.modelTranslatorDb != null) {
                            a.translator = mapper.map(
                                modeldb.modelTranslatorDb!!,
                                ModelTranslator::class.java
                            ) as ModelTranslator
                        }

                        if (modeldb.modelFileDb != null) {
                            a.modelFile = mapper.map(
                                modeldb.modelFileDb!!,
                                ModelFile::class.java
                            ) as ModelFile
                        }

                        dataCore.add(a)
                    }
                    return@map dataCore
                }
            },
            Observable.defer {
                apiHandler.getRecentList().subscribeOn(Schedulers.io()).map {
                    localDataSource.removeAllRecent()
                    var recetIds = arrayListOf<ModelRecentDb>()
                    var data = arrayListOf<ModelBookDb>()
                    it.forEach { modelapi ->
                        recetIds.add(ModelRecentDb(modelapi.bid!!))

                        var a = mapper.map(modelapi, ModelBookDb::class.java) as ModelBookDb
                        if (modelapi.auther != null) {
                            a.auther = mapper.map(
                                modelapi.auther!!,
                                ModelAutherDb::class.java
                            ) as ModelAutherDb
                        }

                        if (modelapi.translator != null) {
                            a.translator = mapper.map(
                                modelapi.translator!!,
                                ModelTranslatorDb::class.java
                            ) as ModelTranslatorDb
                        }

                        data.add(a)
                    }
                    localDataSource.saveBooks(data)
                    localDataSource.saveRecent(recetIds)

                    var dataCore = arrayListOf<ModelBook>()
                    it.forEach { modelapi ->
                        var a = mapper.map(modelapi, ModelBook::class.java) as ModelBook
                        if (modelapi.auther != null) {
                            a.auther = mapper.map(
                                modelapi.auther!!,
                                ModelAuther::class.java
                            ) as ModelAuther
                        }

                        if (modelapi.translator != null) {
                            a.translator = mapper.map(
                                modelapi.translator!!,
                                ModelTranslator::class.java
                            ) as ModelTranslator
                        }

                        dataCore.add(a)
                    }
                    return@map dataCore

                }
            }
        )

    }

    fun getRecomList(): Observable<List<ModelBook>> {
        return Observable.concatArrayEager(
            localDataSource.getAllRecom().subscribeOn(Schedulers.io()).flatMap { its ->
                Log.e("RECENT", "LOCAL " + its.size)
                var ids = arrayListOf<Int>()
                its.forEach { item ->
                    item.bookId?.let { ids.add(it) }
                }
                localDataSource.getBooksByIds(ids).subscribeOn(Schedulers.io()).map {
                    Log.e("RECENT", "LOCAL DATA" + its.size)
                    var dataCore = arrayListOf<ModelBook>()
                    it.forEach { modeldb ->
                        var a = mapper.map(modeldb.modelBookDb, ModelBook::class.java) as ModelBook
                        if (modeldb.modelAutherDb != null) {
                            a.auther = mapper.map(
                                modeldb.modelAutherDb!!,
                                ModelAuther::class.java
                            ) as ModelAuther
                        }

                        if (modeldb.modelTranslatorDb != null) {
                            a.translator = mapper.map(
                                modeldb.modelTranslatorDb!!,
                                ModelTranslator::class.java
                            ) as ModelTranslator
                        }

                        if (modeldb.modelFileDb != null) {
                            a.modelFile = mapper.map(
                                modeldb.modelFileDb!!,
                                ModelFile::class.java
                            ) as ModelFile
                        }

                        dataCore.add(a)
                    }
                    return@map dataCore
                }
            },
            Observable.defer {
                apiHandler.getRecomList().subscribeOn(Schedulers.io()).map {
                    Log.e("RECENT", "REMOTE " + it.size)
                    localDataSource.removeAllRecom()
                    var recetIds = arrayListOf<ModelRecomDb>()
                    var data = arrayListOf<ModelBookDb>()
                    it.forEach { modelapi ->
                        recetIds.add(ModelRecomDb(modelapi.bid!!))
                        var a = mapper.map(modelapi, ModelBookDb::class.java) as ModelBookDb
                        if (modelapi.auther != null) {
                            a.auther = mapper.map(
                                modelapi.auther!!,
                                ModelAutherDb::class.java
                            ) as ModelAutherDb
                        }

                        if (modelapi.translator != null) {
                            a.translator = mapper.map(
                                modelapi.translator!!,
                                ModelTranslatorDb::class.java
                            ) as ModelTranslatorDb
                        }

                        data.add(a)
                    }
                    localDataSource.saveBooks(data)
                    localDataSource.saveRecom(recetIds)

                    var dataCore = arrayListOf<ModelBook>()
                    it.forEach { modelapi ->
                        var a = mapper.map(modelapi, ModelBook::class.java) as ModelBook
                        if (modelapi.auther != null) {
                            a.auther = mapper.map(
                                modelapi.auther!!,
                                ModelAuther::class.java
                            ) as ModelAuther
                        }

                        if (modelapi.translator != null) {
                            a.translator = mapper.map(
                                modelapi.translator!!,
                                ModelTranslator::class.java
                            ) as ModelTranslator
                        }

                        dataCore.add(a)
                    }
                    return@map dataCore

                }
            }
        )

    }

    fun getBook(bid: Int): Observable<ModelBook> {
        return Observable.concatArrayEager(
            localDataSource.getBooksByIds(bid).subscribeOn(Schedulers.io()).map {
                var dataCore = mapper.map(it.modelBookDb, ModelBook::class.java) as ModelBook
                if (it.modelAutherDb != null) {
                    dataCore.auther =
                        mapper.map(it.modelAutherDb!!, ModelAuther::class.java) as ModelAuther
                }

                if (it.modelTranslatorDb != null) {
                    dataCore.translator = mapper.map(
                        it.modelTranslatorDb!!,
                        ModelTranslator::class.java
                    ) as ModelTranslator
                }

                if (it.modelFileDb != null) {
                    dataCore.modelFile =
                        mapper.map(it.modelFileDb!!, ModelFile::class.java) as ModelFile
                }

                if (it.modelFavoriteDb != null) {
                    dataCore.modelFavorite =
                        mapper.map(it.modelFavoriteDb!!, ModelFavorite::class.java) as ModelFavorite
                }


                return@map dataCore
            }
        )
    }

    fun getCategoryBook(catId: Int): Observable<List<ModelBook>> {
        return Observable.concatArrayEager(
            localDataSource.getBooksByCategorys(catId).subscribeOn(Schedulers.io()).map {
                var dataCore = arrayListOf<ModelBook>()
                it.forEach { modeldb ->
                    var a = mapper.map(modeldb.modelBookDb, ModelBook::class.java) as ModelBook
                    a.auther =
                        modeldb.modelAutherDb?.let { it1 ->
                            mapper.map(
                                it1,
                                ModelAuther::class.java
                            )
                        } as ModelAuther
                    a.translator =
                        modeldb.modelAutherDb?.let { it1 ->
                            mapper.map(
                                it1,
                                ModelTranslator::class.java
                            )
                        } as ModelTranslator

                    if (modeldb.modelFileDb != null) {
                        a.modelFile =
                            mapper.map(modeldb.modelFileDb!!, ModelFile::class.java) as ModelFile
                    }

                    dataCore.add(a)
                }
                return@map dataCore
            }, Observable.defer {
                apiHandler.getBookList(catId).subscribeOn(Schedulers.io()).map {
                    var recetIds = arrayListOf<ModelRecomDb>()
                    var data = arrayListOf<ModelBookDb>()
                    it.forEach { modelapi ->
                        recetIds.add(ModelRecomDb(modelapi.bid!!))
                        var a = mapper.map(modelapi, ModelBookDb::class.java) as ModelBookDb
                        if (modelapi.auther != null) {
                            a.auther = mapper.map(
                                modelapi.auther!!,
                                ModelAutherDb::class.java
                            ) as ModelAutherDb
                        }

                        if (modelapi.translator != null) {
                            a.translator = mapper.map(
                                modelapi.translator!!,
                                ModelTranslatorDb::class.java
                            ) as ModelTranslatorDb
                        }

                        data.add(a)
                    }
                    localDataSource.saveBooks(data)

                    var dataCore = arrayListOf<ModelBook>()
                    it.forEach { modelapi ->
                        var a = mapper.map(modelapi, ModelBook::class.java) as ModelBook
                        if (modelapi.auther != null) {
                            a.auther = mapper.map(
                                modelapi.auther!!,
                                ModelAuther::class.java
                            ) as ModelAuther
                        }

                        if (modelapi.translator != null) {
                            a.translator = mapper.map(
                                modelapi.translator!!,
                                ModelTranslator::class.java
                            ) as ModelTranslator
                        }

                        dataCore.add(a)
                    }
                    return@map dataCore

                }
            }
        )
    }

    fun getShelfBook(): Observable<List<ModelBook>> {
        return localDataSource.getAllFile().subscribeOn(Schedulers.io()).flatMap { its ->
            var ids = arrayListOf<Int>()
            its.forEach { item ->
                item.bookId?.let { ids.add(it) }
            }
            localDataSource.getBooksByIds(ids).subscribeOn(Schedulers.io()).map {
                var dataCore = arrayListOf<ModelBook>()
                it.forEach { modeldb ->
                    var a = mapper.map(modeldb.modelBookDb, ModelBook::class.java) as ModelBook
                    if (modeldb.modelAutherDb != null) {
                        a.auther = mapper.map(
                            modeldb.modelAutherDb!!,
                            ModelAuther::class.java
                        ) as ModelAuther
                    }

                    if (modeldb.modelTranslatorDb != null) {
                        a.translator = mapper.map(
                            modeldb.modelTranslatorDb!!,
                            ModelTranslator::class.java
                        ) as ModelTranslator
                    }

                    if (modeldb.modelFileDb != null) {
                        a.modelFile =
                            mapper.map(modeldb.modelFileDb!!, ModelFile::class.java) as ModelFile
                    }

                    dataCore.add(a)
                }
                return@map dataCore
            }
        }
    }

    fun getFavoriteBook(): Observable<List<ModelBook>> {
        return localDataSource.getAllFavorite().subscribeOn(Schedulers.io()).flatMap { its ->
            var ids = arrayListOf<Int>()
            its.forEach { item ->
                item.bookId?.let { ids.add(it) }
            }
            localDataSource.getBooksByIds(ids).subscribeOn(Schedulers.io()).map {
                var dataCore = arrayListOf<ModelBook>()
                it.forEach { modeldb ->
                    var a = mapper.map(modeldb.modelBookDb, ModelBook::class.java) as ModelBook
                    if (modeldb.modelAutherDb != null) {
                        a.auther = mapper.map(
                            modeldb.modelAutherDb!!,
                            ModelAuther::class.java
                        ) as ModelAuther
                    }

                    if (modeldb.modelTranslatorDb != null) {
                        a.translator = mapper.map(
                            modeldb.modelTranslatorDb!!,
                            ModelTranslator::class.java
                        ) as ModelTranslator
                    }

                    if (modeldb.modelFileDb != null) {
                        a.modelFile =
                            mapper.map(modeldb.modelFileDb!!, ModelFile::class.java) as ModelFile
                    }

                    dataCore.add(a)
                }
                return@map dataCore
            }
        }
    }

    fun updateLocalFileName(data: ModelBookDb, name: String): Completable {
        var file = ModelFileDb(name, data.bid!!)
        return localDataSource.insertFile(file)

    }

    fun getFile(bid: Int): Observable<ModelFile> {
        return localDataSource.getFile(bid).subscribeOn(Schedulers.io()).map {
            return@map mapper.map(it, ModelFile::class.java) as ModelFile
        }
    }

    fun deleteFile(bid: Int): Completable {
        return localDataSource.deleteFile(bid)
    }

    fun addToFavorite(bid:Int): Completable{
        var model=ModelFavoriteDb()
        model.bookId=bid
        return localDataSource.insertFavorite(model)
    }

    fun removeFromFavorite(bid:Int): Completable{
        return localDataSource.deleteFavorite(bid)
    }

    fun removeALl(){
        localDataSource.deleteAllFavorite()
        localDataSource.removeAllRecent()
        localDataSource.removeAllCategory()
        localDataSource.removeAllAuther()
        localDataSource.removeAllBooks()
        localDataSource.removeAllRecom()
        localDataSource.deleteFile()
        localDataSource.removeAllTranslator()
    }
}