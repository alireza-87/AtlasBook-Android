package com.midnightgeek.atlasbook.repo.local.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_catg")
class ModelCategoryDb constructor(name: String, desc: String, image: String, cid: Int) {
    constructor() : this("", "", "", 0)

    var name: String? = name
    var desc: String? = desc
    var image: String? = image

    @PrimaryKey
    var cid: Int? = cid

}