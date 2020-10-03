package com.midnightgeek.atlasbook.repo.local.interfaces

import com.midnightgeek.atlasbook.repo.local.models.entity.*
import com.midnightgeek.atlasbook.repo.local.models.query.ModelQueryBook
import io.reactivex.Completable
import io.reactivex.Observable

interface LocalRepo {
    //category
    fun getAllCategory(): Observable<List<ModelCategoryDb>>
    fun saveCategory(data: ModelCategoryDb)
    fun saveCategory(data: List<ModelCategoryDb>)
    fun removeAllCategory(): Completable

    //Auther
    fun getAllAuther(): Observable<List<ModelAutherDb>>
    fun saveAuther(data: ModelAutherDb)
    fun saveAuther(data: List<ModelAutherDb>)
    fun removeAllAuther(): Completable

    //Translator
    fun getAllTranslator(): Observable<List<ModelTranslatorDb>>
    fun saveTranslator(data: ModelTranslatorDb)
    fun saveTranslator(data: List<ModelTranslatorDb>)
    fun removeAllTranslator(): Completable

    //Books
    fun getAllBooks(): Observable<List<ModelQueryBook>>
    fun saveBooks(data: ModelBookDb)
    fun saveBooks(data: List<ModelBookDb>)
    fun removeAllBooks(): Completable
    fun getBooksByIds(data: List<Int>): Observable<List<ModelQueryBook>>
    fun getBooksByIds(data: Int): Observable<ModelQueryBook>
    fun getBooksByCategorys(data: Int): Observable<List<ModelQueryBook>>
    fun updateBook(data: ModelBookDb): Completable
    fun getShelfBook(): Observable<List<ModelQueryBook>>

    //Recent
    fun getAllRecent(): Observable<List<ModelRecentDb>>
    fun saveRecent(data: ModelRecentDb)
    fun saveRecent(data: List<ModelRecentDb>)
    fun removeAllRecent()

    //Recommend
    fun getAllRecom(): Observable<List<ModelRecomDb>>
    fun saveRecom(data: ModelRecomDb)
    fun saveRecom(data: List<ModelRecomDb>)
    fun removeAllRecom()

    //file
    fun insertFile(data: ModelFileDb): Completable
    fun getFile(bid: Int): Observable<ModelFileDb>
    fun getAllFile(): Observable<List<ModelFileDb>>
    fun deleteFile(bid: Int): Completable
    fun deleteFile()

    //favorite
    fun getAllFavorite(): Observable<List<ModelFavoriteDb>>
    fun insertFavorite(data:ModelFavoriteDb): Completable
    fun deleteFavorite(bid:Int): Completable
    fun deleteAllFavorite()
}