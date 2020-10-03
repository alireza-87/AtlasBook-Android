package com.midnightgeek.atlasbook.repo.local.models.query

import androidx.room.Embedded
import androidx.room.Relation
import com.midnightgeek.atlasbook.repo.local.models.entity.*

class ModelQueryBook {
    @Embedded
    var modelBookDb: ModelBookDb = ModelBookDb()

    @Transient
    @Relation(
        parentColumn = "autherId",
        entityColumn = "aid",
        entity = ModelAutherDb::class
    )
    var modelAutherDb: ModelAutherDb? = null


    @Transient
    @Relation(
        parentColumn = "translatorId",
        entityColumn = "tid",
        entity = ModelTranslatorDb::class
    )
    var modelTranslatorDb: ModelTranslatorDb? = null

    @Transient
    @Relation(
        parentColumn = "bid",
        entityColumn = "bookId",
        entity = ModelFileDb::class
    )
    var modelFileDb: ModelFileDb? = null

    @Transient
    @Relation(
        parentColumn = "bid",
        entityColumn = "bookId",
        entity = ModelFavoriteDb::class
    )
    var modelFavoriteDb: ModelFavoriteDb? = null

}