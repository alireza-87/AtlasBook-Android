package com.midnightgeek.atlasbook.repo.local

import com.midnightgeek.atlasbook.repo.local.interfaces.*
import com.midnightgeek.atlasbook.repo.local.models.entity.*
import com.midnightgeek.atlasbook.repo.local.models.query.ModelQueryBook
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private var categoryDao: CategoryDao,
    private var autherDao: AutherDao,
    private var translatorDao: TranslatorDao,
    private var bookDao: BookDao,
    private var recentDao: RecentDao,
    private var recomDao: RecomDao,
    private var fileDao: FileDao,
    private var favoriteDao: FavoriteDao
) : LocalRepo {
    // Category
    override fun getAllCategory(): Observable<List<ModelCategoryDb>> {
        return categoryDao.fetchAllCatgory()
    }

    override fun saveCategory(data: ModelCategoryDb) {
        return categoryDao.saveCatgory(data)
    }

    override fun saveCategory(data: List<ModelCategoryDb>) {
        categoryDao.saveCatgory(data)
    }

    override fun removeAllCategory(): Completable {
        return Completable.fromCallable { categoryDao.deleteAll() }
    }

    // Auther
    override fun getAllAuther(): Observable<List<ModelAutherDb>> {
        return autherDao.fetchAllAuther()
    }

    override fun saveAuther(data: ModelAutherDb) {
        autherDao.saveAuther(data)
    }

    override fun saveAuther(data: List<ModelAutherDb>) {
        return autherDao.saveAuther(data)
    }

    override fun removeAllAuther(): Completable {
        return Completable.fromCallable { autherDao.deleteAll() }
    }

    override fun getAllTranslator(): Observable<List<ModelTranslatorDb>> {
        return translatorDao.fetchAllTranslator()
    }

    // Translator
    override fun saveTranslator(data: ModelTranslatorDb) {
        translatorDao.saveTranslator(data)
    }

    override fun saveTranslator(data: List<ModelTranslatorDb>) {
        return translatorDao.saveTranslator(data)
    }

    override fun removeAllTranslator(): Completable {
        return Completable.fromCallable { translatorDao.deleteAll() }
    }

    //Book

    override fun getAllBooks(): Observable<List<ModelQueryBook>> {
        return bookDao.fetchAllBook()
    }

    override fun saveBooks(data: ModelBookDb) {
        bookDao.saveBook(data)
        data.auther?.let { it1 -> this.saveAuther(it1) }
        data.translator?.let { it1 -> this.saveTranslator(it1) }
    }

    override fun saveBooks(data: List<ModelBookDb>) {
        bookDao.saveBook(data)
        var autherList = ArrayList<ModelAutherDb>()
        var translatorList = ArrayList<ModelTranslatorDb>()
        data.forEach {
            it.auther?.let { it1 -> autherList.add(it1) }
            it.translator?.let { it1 -> translatorList.add(it1) }
        }
        this.saveAuther(autherList)
        this.saveTranslator(translatorList)
    }

    override fun removeAllBooks(): Completable {
        return Completable.fromCallable { bookDao.deleteAll() }

    }

    override fun getBooksByIds(data: List<Int>): Observable<List<ModelQueryBook>> {
        return bookDao.fetchBookByIds(data)
    }

    override fun getBooksByIds(data: Int): Observable<ModelQueryBook> {
        return bookDao.fetchBookByIds(data)
    }

    override fun getBooksByCategorys(data: Int): Observable<List<ModelQueryBook>> {
        return bookDao.fetchBookByCategorys(data)
    }

    override fun updateBook(data: ModelBookDb): Completable {
        return bookDao.updateBook(data)
    }

    override fun getShelfBook(): Observable<List<ModelQueryBook>> {
        return bookDao.fetchShelfBook()
    }

    //recent
    override fun getAllRecent(): Observable<List<ModelRecentDb>> {
        return recentDao.fetchAllRecent()
    }

    override fun saveRecent(data: ModelRecentDb) {
        recentDao.saveRecent(data)
    }

    override fun saveRecent(data: List<ModelRecentDb>) {
        recentDao.saveRecent(data)
    }

    override fun removeAllRecent() {
        return recentDao.deleteAll()
    }

    //Recommenec
    override fun getAllRecom(): Observable<List<ModelRecomDb>> {
        return recomDao.fetchAllRecomm()
    }

    override fun saveRecom(data: ModelRecomDb) {
        recomDao.saveRecomm(data)
    }

    override fun saveRecom(data: List<ModelRecomDb>) {
        recomDao.saveRecomm(data)
    }

    override fun removeAllRecom() {
        return recomDao.deleteAll()
    }

    //file
    override fun insertFile(data: ModelFileDb): Completable {
        return fileDao.insert(data)
    }

    override fun getFile(bid: Int): Observable<ModelFileDb> {
        return fileDao.getFileName(bid)
    }

    override fun getAllFile(): Observable<List<ModelFileDb>> {
        return fileDao.getAllFileName()
    }

    override fun deleteFile(bid: Int): Completable {
        return fileDao.deleteAll(bid)
    }

    override fun deleteFile() {
        return fileDao.deleteAll()
    }

    // favorite
    override fun getAllFavorite(): Observable<List<ModelFavoriteDb>> {
        return favoriteDao.fetchAllFavoriteId()
    }

    override fun insertFavorite(data: ModelFavoriteDb): Completable {
        return favoriteDao.saveFavorite(data)
    }

    override fun deleteFavorite(bid: Int): Completable {
        return favoriteDao.deleteFavorite(bid)
    }

    override fun deleteAllFavorite() {
        return favoriteDao.deleteAllFavorite()
    }


}