package com.midnightgeek.atlasbook.repo.local.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelFileDb
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: ModelFileDb): Completable

    @Query("SELECT * from tbl_file where bookId = :bid")
    fun getFileName(bid: Int): Observable<ModelFileDb>

    @Query("SELECT * from tbl_file")
    fun getAllFileName(): Observable<List<ModelFileDb>>

    @Query("DELETE FROM tbl_file where bookId = :bid")
    fun deleteAll(bid: Int): Completable

    @Query("DELETE FROM tbl_file")
    fun deleteAll()

}