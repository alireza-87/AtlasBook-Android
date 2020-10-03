package com.midnightgeek.atlasbook.di.moduls

import android.app.Application
import com.midnightgeek.atlasbook.utils.StorageHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Singleton
    @Provides
    fun provideStorageHandler(application: Application?): StorageHandler {
        return StorageHandler(application!!)
    }
}