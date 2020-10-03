package com.midnightgeek.atlasbook.repo.local.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelRecomDb
import io.reactivex.Observable

@Dao
interface RecomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRecomm(data: ModelRecomDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRecomm(data: List<ModelRecomDb>)

    @Query("DELETE FROM tbl_recommm")
    fun deleteAll()

    @Query("SELECT * FROM tbl_recommm")
    fun fetchAllRecomm(): Observable<List<ModelRecomDb>>

}