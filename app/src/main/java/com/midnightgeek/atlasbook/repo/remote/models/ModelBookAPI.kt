package com.midnightgeek.atlasbook.repo.remote.models


class ModelBookAPI {
    var bid: Int? = 0
    lateinit var name: String
    lateinit var desc: String
    lateinit var image: String
    lateinit var fileName: String
    var catg: Int? = 0
    var auther: ModelAutherAPI? = null
    var translator: ModelTranslatorAPI? = null
}