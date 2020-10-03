package com.midnightgeek.atlasbook.models

class ModelAuther constructor(
    var aid: Int?,
    var name: String?,
    var desc: String?,
    var image: String?
) {
    constructor() : this(9, "", "", "")

}