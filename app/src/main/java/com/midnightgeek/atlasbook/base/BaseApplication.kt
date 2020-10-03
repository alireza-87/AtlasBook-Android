package com.midnightgeek.atlasbook.base

import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.FirebaseApp
import com.liulishuo.filedownloader.FileDownloader
import com.midnightgeek.atlasbook.di.components.AppComponents
import com.midnightgeek.atlasbook.di.components.DaggerAppComponents
import com.midnightgeek.atlasbook.utils.FcmUtils
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

/**
 * <p>Application Class</p>
 *  Application initializer
 */
class BaseApplication : DaggerApplication() {

    @Inject
    lateinit var fcmUtils: FcmUtils

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        FirebaseApp.initializeApp(this)
        FileDownloader.setup(this)
        fcmUtils.checkFcmToken()

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: AppComponents =
            DaggerAppComponents.builder().application(this)?.build()!!
        component.inject(this)
        return component
    }
}