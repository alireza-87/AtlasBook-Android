package com.midnightgeek.atlasbook.di.moduls

import com.midnightgeek.atlasbook.utils.FcmUtils
import com.midnightgeek.atlasbook.utils.PrefHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FcmModule {
    @Singleton
    @Provides
    fun provideFcmUtils(prefHandler: PrefHandler): FcmUtils {
        return FcmUtils(prefHandler)
    }
}