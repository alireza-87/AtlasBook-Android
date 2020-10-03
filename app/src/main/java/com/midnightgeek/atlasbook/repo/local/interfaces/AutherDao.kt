package com.midnightgeek.atlasbook.repo.local.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelAutherDb
import io.reactivex.Observable


@Dao
interface AutherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAuther(data: ModelAutherDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAuther(data: List<ModelAutherDb>)

    @Query("DELETE FROM tbl_auther")
    fun deleteAll()

    @Query("SELECT * FROM tbl_auther")
    fun fetchAllAuther(): Observable<List<ModelAutherDb>>

}