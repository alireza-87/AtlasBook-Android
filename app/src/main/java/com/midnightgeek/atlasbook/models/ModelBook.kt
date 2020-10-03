package com.midnightgeek.atlasbook.models

class ModelBook constructor(
    var bid: Int?,
    var name: String?,
    var desc: String?,
    var image: String?,
    var fileName: String?,
    var sFileName: String?,
    var catg: Int?,
    var auther: ModelAuther?,
    var translator: ModelTranslator?,
    var modelFile: ModelFile?,
    var modelFavorite: ModelFavorite?
) {
    constructor() : this(0, "", "", "", "", "", 0, null, null, null,null)
}