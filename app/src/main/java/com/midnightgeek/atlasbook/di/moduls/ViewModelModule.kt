package com.midnightgeek.atlasbook.di.moduls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.midnightgeek.atlasbook.MainActivityViewModel
import com.midnightgeek.atlasbook.di.utils.ViewModelKey
import com.midnightgeek.atlasbook.ui.*
import com.midnightgeek.atlasbook.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivitytViewModel(mainActivityViewModel: MainActivityViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindMainFragmentViewModel(mainFragmentViewModel: MainFragmentViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginFragmentViewModel(loginViewModel: LoginViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(BookFragmentViewModel::class)
    abstract fun bindBookFragmentViewModel(bookFragmentViewModel: BookFragmentViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(BookListViewModel::class)
    abstract fun bindBookListFragmentViewModel(bookListFragmentViewModel: BookListViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(ReaderViewModel::class)
    abstract fun bindReaderFragmentViewModel(readerViewModel: ReaderViewModel): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileFragmentViewModel(profileViewModel: ProfileViewModel): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(CategoryListViewModel::class)
    abstract fun bindCagtegoryListViewModel(categoryListViewModel: CategoryListViewModel):ViewModel?

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory?): ViewModelProvider.Factory?

}