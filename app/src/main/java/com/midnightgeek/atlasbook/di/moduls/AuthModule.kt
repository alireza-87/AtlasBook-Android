package com.midnightgeek.atlasbook.di.moduls

import android.app.Application
import com.midnightgeek.atlasbook.utils.Authonticator
import com.midnightgeek.atlasbook.utils.PrefHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {
    @Singleton
    @Provides
    fun getAuthonticator(application: Application?,prefHandler: PrefHandler): Authonticator {
        return Authonticator(application!!,prefHandler)
    }
}