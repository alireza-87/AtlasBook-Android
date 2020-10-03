package com.midnightgeek.atlasbook.di.moduls

import com.midnightgeek.atlasbook.utils.Mapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {
    @Provides
    @Singleton
    fun providMapper():Mapper{
        return Mapper()
    }
}