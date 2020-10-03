package com.midnightgeek.atlasbook.repo.local.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_books")
class ModelBookDb constructor(
    bid: Int,
    name: String,
    desc: String,
    image: String,
    catg: Int,
    auther: Int,
    translator: Int,
    sFileName: String,
    lFileName: String
) {
    constructor() : this(0, "", "", "", 0, 0, 0, "", "")

    @PrimaryKey
    var bid: Int? = bid
    var name: String? = name
    var desc: String? = desc
    var image: String? = image
    var catg: Int? = catg
    var autherId: Int = auther
    var translatorId: Int = translator
    var sFileName: String = sFileName
    var lFileName: String = lFileName

    @Transient
    var auther: ModelAutherDb? = null

    @Transient
    var translator: ModelTranslatorDb? = null

}