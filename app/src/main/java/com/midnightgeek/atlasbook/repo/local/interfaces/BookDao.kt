package com.midnightgeek.atlasbook.repo.local.interfaces

import androidx.room.*
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelBookDb
import com.midnightgeek.atlasbook.repo.local.models.query.ModelQueryBook
import io.reactivex.Completable
import io.reactivex.Observable


@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBook(data: ModelBookDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBook(data: List<ModelBookDb>)

    @Query("DELETE FROM tbl_books")
    fun deleteAll()

    @Query("SELECT * FROM tbl_books")
    fun fetchAllBook(): Observable<List<ModelQueryBook>>

    @Query("SELECT * FROM tbl_books where lFileName!=''")
    fun fetchShelfBook(): Observable<List<ModelQueryBook>>

    @Query("SELECT * FROM tbl_books where bid IN (:ids) ORDER BY bid DESC")
    fun fetchBookByIds(ids: List<Int>): Observable<List<ModelQueryBook>>

    @Query("SELECT * FROM tbl_books where bid = :ids")
    fun fetchBookByIds(ids: Int): Observable<ModelQueryBook>

    @Query("SELECT * FROM tbl_books where catg = :ids")
    fun fetchBookByCategorys(ids: Int): Observable<List<ModelQueryBook>>

    @Update
    fun updateBook(data: ModelBookDb?): Completable

}