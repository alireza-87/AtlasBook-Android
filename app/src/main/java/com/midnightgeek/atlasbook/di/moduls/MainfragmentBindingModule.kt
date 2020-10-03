package com.midnightgeek.atlasbook.di.moduls

import com.midnightgeek.atlasbook.ui.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainfragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun provideLoginFragment(): LoginFragment?

    @ContributesAndroidInjector
    abstract fun provideMainFragment(): MainFragment?

    @ContributesAndroidInjector
    abstract fun provideBookFragment(): BookFragment?

    @ContributesAndroidInjector
    abstract fun provideBookListFragment(): BookListFragment?

    @ContributesAndroidInjector
    abstract fun provideReaderFragment(): ReaderFragment?

    @ContributesAndroidInjector
    abstract fun provideProfileFragment(): ProfileFragment?

    @ContributesAndroidInjector
    abstract fun provideCategoryFragment():CategoryList?

}