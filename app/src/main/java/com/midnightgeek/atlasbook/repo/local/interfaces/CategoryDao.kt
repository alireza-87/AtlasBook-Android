package com.midnightgeek.atlasbook.repo.local.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelCategoryDb
import io.reactivex.Observable

@Dao
interface CategoryDao {
    @Query("SELECT * FROM tbl_catg")
    fun fetchAllCatgory(): Observable<List<ModelCategoryDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCatgory(data: ModelCategoryDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCatgory(data: List<ModelCategoryDb>)

    @Query("DELETE FROM tbl_catg")
    fun deleteAll()

}