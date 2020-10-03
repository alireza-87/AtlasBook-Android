package com.midnightgeek.atlasbook.repo.local.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_recent")
class ModelRecentDb constructor(bookId: Int) {
    constructor() : this(0)

    @PrimaryKey
    var bookId: Int? = bookId

}