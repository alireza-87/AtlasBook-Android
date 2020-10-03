package com.midnightgeek.atlasbook.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.midnightgeek.atlasbook.repo.local.interfaces.*
import com.midnightgeek.atlasbook.repo.local.models.entity.*

@Database(
    entities = [ModelFavoriteDb::class,ModelFileDb::class, ModelCategoryDb::class, ModelAutherDb::class, ModelTranslatorDb::class, ModelBookDb::class, ModelRecentDb::class, ModelRecomDb::class],
    version = 1,
    exportSchema = false
)
abstract class DataBaseLocal : RoomDatabase() {
    abstract val categoryDao: CategoryDao?
    abstract val autherDao: AutherDao?
    abstract val translatorDao: TranslatorDao?
    abstract val bookDao: BookDao?
    abstract val recentDao: RecentDao?
    abstract val recomDao: RecomDao?
    abstract val fileDao: FileDao?
    abstract val favoriteDao:FavoriteDao?
}
