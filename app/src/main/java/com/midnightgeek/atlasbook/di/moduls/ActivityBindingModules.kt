package com.midnightgeek.atlasbook.di.moduls

import com.midnightgeek.atlasbook.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModules {
    @ContributesAndroidInjector(modules = [MainfragmentBindingModule::class])
    abstract fun bindMainactivity(): MainActivity?
}