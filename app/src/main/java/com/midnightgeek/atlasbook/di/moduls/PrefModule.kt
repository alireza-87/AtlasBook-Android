package com.midnightgeek.atlasbook.di.moduls

import android.app.Application
import com.midnightgeek.atlasbook.utils.PrefHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefModule {
    @Singleton
    @Provides
    fun providePrefHandler(application: Application?): PrefHandler {
        return PrefHandler(application!!)
    }

}