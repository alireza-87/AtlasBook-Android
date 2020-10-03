package com.midnightgeek.atlasbook.models

class ModelTranslator constructor(
    var tid: Int?,
    var name: String?,
    var desc: String?,
    var image: String?
) {
    constructor() : this(0, "", "", "")
}