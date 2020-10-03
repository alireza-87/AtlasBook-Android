package com.midnightgeek.atlasbook.repo.local.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_auther")
class ModelAutherDb constructor(aid: Int, name: String, desc: String, image: String) {
    constructor() : this(0, "", "", "")

    @PrimaryKey
    var aid: Int? = aid
    var name: String? = name
    var desc: String? = desc
    var image: String? = image


}