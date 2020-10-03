package com.midnightgeek.atlasbook.di.components

import android.app.Application
import com.midnightgeek.atlasbook.base.BaseApplication
import com.midnightgeek.atlasbook.di.moduls.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AuthModule::class,MapperModule::class, FcmModule::class, AdmobModule::class, StorageModule::class, PrefModule::class, DataModule::class, AppModule::class, AndroidSupportInjectionModule::class, MainfragmentBindingModule::class, ActivityBindingModules::class])
interface AppComponents : AndroidInjector<DaggerApplication> {
    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): AppComponents.Builder?
        fun build(): AppComponents?
    }

}