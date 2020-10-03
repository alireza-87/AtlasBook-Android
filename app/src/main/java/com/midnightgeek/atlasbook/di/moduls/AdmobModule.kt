package com.midnightgeek.atlasbook.di.moduls

import android.app.Application
import com.google.android.gms.ads.InterstitialAd
import com.midnightgeek.atlasbook.utils.AdsHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AdmobModule {
    @Singleton
    @Provides
    fun provideInterstitialAd(application: Application?): InterstitialAd {
        var mInterstitialAd = InterstitialAd(application)
        mInterstitialAd.adUnitId = "ca-app-pub-XXXXXX/XXXXXX"
        return mInterstitialAd
    }

    @Singleton
    @Provides
    fun provideAdsHandlder(interstitialAd: InterstitialAd): AdsHandler {
        return AdsHandler(interstitialAd)
    }
}