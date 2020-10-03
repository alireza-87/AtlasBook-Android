package com.midnightgeek.atlasbook.repo.local.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelRecentDb
import io.reactivex.Observable

@Dao
interface RecentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRecent(data: ModelRecentDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRecent(data: List<ModelRecentDb>)

    @Query("DELETE FROM tbl_recent")
    fun deleteAll()

    @Query("SELECT * FROM tbl_recent")
    fun fetchAllRecent(): Observable<List<ModelRecentDb>>

}