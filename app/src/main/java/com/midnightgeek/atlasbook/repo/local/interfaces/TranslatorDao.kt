package com.midnightgeek.atlasbook.repo.local.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.midnightgeek.atlasbook.repo.local.models.entity.ModelTranslatorDb
import io.reactivex.Observable

@Dao
interface TranslatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTranslator(data: ModelTranslatorDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTranslator(data: List<ModelTranslatorDb>)

    @Query("DELETE FROM tbl_translator")
    fun deleteAll()

    @Query("SELECT * FROM tbl_translator")
    fun fetchAllTranslator(): Observable<List<ModelTranslatorDb>>


}