package com.midnightgeek.atlasbook.repo.local.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelFavoriteDb
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM tbl_favorite")
    fun fetchAllFavoriteId(): Observable<List<ModelFavoriteDb>>

    @Insert
    fun saveFavorite(data:ModelFavoriteDb): Completable

    @Query("DELETE FROM tbl_favorite where bookId = :bid")
    fun deleteFavorite(bid:Int): Completable

    @Query("DELETE FROM tbl_favorite")
    fun deleteAllFavorite()

}