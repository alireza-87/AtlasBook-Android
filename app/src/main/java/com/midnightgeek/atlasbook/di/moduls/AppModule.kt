package com.midnightgeek.atlasbook.di.moduls

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun providesApplication(application: Application?): Context?

}