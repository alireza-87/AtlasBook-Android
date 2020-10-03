package com.midnightgeek.atlasbook.di.moduls

import android.app.Application
import androidx.room.Room
import com.midnightgeek.atlasbook.repo.local.DataBaseLocal
import com.midnightgeek.atlasbook.repo.local.LocalDataSource
import com.midnightgeek.atlasbook.repo.local.interfaces.*
import com.midnightgeek.atlasbook.repo.remote.interfaces.RepoServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, AppModule::class])
class DataModule {
    private val BASE_URL = "https://www.atlas-shelf.me/"

    @Singleton
    @Provides
    fun provideRetrofit(application: Application?): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): RepoServices {
        return retrofit.create(RepoServices::class.java)
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application?): DataBaseLocal {
        return Room.databaseBuilder(
            application!!,
            DataBaseLocal::class.java, "DataBaseBook.db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesCategoryDao(database: DataBaseLocal): CategoryDao {
        return database.categoryDao!!
    }

    @Singleton
    @Provides
    fun providesAutherDao(database: DataBaseLocal): AutherDao {
        return database.autherDao!!
    }

    @Singleton
    @Provides
    fun providesTranslatrDao(database: DataBaseLocal): TranslatorDao {
        return database.translatorDao!!
    }

    @Singleton
    @Provides
    fun providesBookDao(database: DataBaseLocal): BookDao {
        return database.bookDao!!
    }

    @Singleton
    @Provides
    fun provideRecentDao(database: DataBaseLocal): RecentDao {
        return database.recentDao!!
    }

    @Singleton
    @Provides
    fun provideRecomDao(database: DataBaseLocal): RecomDao {
        return database.recomDao!!
    }

    @Singleton
    @Provides
    fun provideFileDao(database: DataBaseLocal): FileDao {
        return database.fileDao!!
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(database: DataBaseLocal): FavoriteDao {
        return database.favoriteDao!!
    }

    @Singleton
    @Provides
    fun provideRepository(
        categoryDao: CategoryDao,
        autherDao: AutherDao,
        translatorDao: TranslatorDao,
        bookDao: BookDao,
        recentDao: RecentDao,
        recomDao: RecomDao,
        fileDao: FileDao,
        favoriteDao: FavoriteDao
    ): LocalRepo {
        return LocalDataSource(
            categoryDao,
            autherDao,
            translatorDao,
            bookDao,
            recentDao,
            recomDao,
            fileDao,
            favoriteDao
        )
    }

}