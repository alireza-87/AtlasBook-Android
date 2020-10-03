package com.midnightgeek.atlasbook.utils

import android.util.Log
import com.midnightgeek.atlasbook.models.*
import com.midnightgeek.atlasbook.repo.local.models.entity.*
import com.midnightgeek.atlasbook.repo.remote.models.ModelAutherAPI
import com.midnightgeek.atlasbook.repo.remote.models.ModelBookAPI
import com.midnightgeek.atlasbook.repo.remote.models.ModelCatrgoryAPI
import com.midnightgeek.atlasbook.repo.remote.models.ModelTranslatorAPI

/**
 * <p>Class Mapper</p>
 * This class used to map application classes to each others
 */
class Mapper {
    private val TAG = "Mapper"
    fun map(data: Any, T: Class<*>): Any? {
        try {
            val result = T.newInstance()
            if (data is ModelCategoryDb) {
                if (result is ModelCategory) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        cid = data.cid
                    }
                }
            } else if (data is ModelCatrgoryAPI) {
                if (result is ModelCategory) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        cid = data.cid
                    }
                } else if (result is ModelCategoryDb) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        cid = data.cid
                    }
                }
            } else if (data is ModelBookAPI) {
                if (result is ModelBookDb) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        bid = data.bid
                        catg = data.catg
                        sFileName = data.fileName
                        if (data.auther != null)
                            autherId = data.auther?.aid!!
                        if (data.translator != null)
                            translatorId = data.translator?.tid!!
                    }
                } else if (result is ModelBook) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        bid = data.bid
                        catg = data.catg
                        sFileName = data.fileName
                    }
                }
            } else if (data is ModelBook) {
                if (result is ModelBookDb) {
                    with(result) {
                        bid = data.bid
                        name = data.name
                        desc = data.desc
                        image = data.image
                        lFileName = data.fileName!!
                        sFileName = data.sFileName!!
                        catg = data.catg
                        if (data.auther != null)
                            autherId = data.auther!!.aid!!
                        if (data.translator != null)
                            translatorId = data.translator!!.tid!!

                    }
                }
            } else if (data is ModelBookDb) {
                if (result is ModelBook) {
                    with(result) {
                        bid = data.bid
                        name = data.name
                        desc = data.desc
                        image = data.image
                        fileName = data.lFileName
                        sFileName = data.sFileName
                        catg = data.catg

                    }
                }
            } else if (data is ModelAutherAPI) {
                if (result is ModelAutherDb) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        aid = data.aid
                    }
                } else if (result is ModelAuther) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        aid = data.aid
                    }
                }
            } else if (data is ModelAutherDb) {
                if (result is ModelAuther) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        aid = data.aid
                    }
                }
            } else if (data is ModelTranslatorAPI) {
                if (result is ModelTranslatorDb) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        tid = data.tid
                    }
                } else if (result is ModelTranslator) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        tid = data.tid
                    }
                }
            } else if (data is ModelTranslatorDb) {
                if (result is ModelTranslator) {
                    with(result) {
                        name = data.name
                        desc = data.desc
                        image = data.image
                        tid = data.tid
                    }
                }
            } else if (data is ModelFileDb) {
                if (result is ModelFile) {
                    with(result) {
                        bookId = data.bookId
                        name = data.name
                    }
                }
            }else if (data is ModelFavoriteDb) {
                if (result is ModelFavorite) {
                    with(result) {
                        bookId = data.bookId
                    }
                }
            } else if (data is ModelFile) {
                if (result is ModelFileDb) {
                    with(result) {
                        bookId = data.bookId
                        name = data.name
                    }
                }
            }
            return result
        } catch (ex: Exception) {
            Log.e(TAG, " Mapper ERRO : $ex")
            return null
        }
    }
}