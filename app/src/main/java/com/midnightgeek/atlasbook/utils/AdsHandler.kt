package com.midnightgeek.atlasbook.utils

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.midnightgeek.atlasbook.ui.callback.AdsDelegate
import javax.inject.Inject

class AdsHandler @Inject constructor(var interstitialAd: InterstitialAd) {
    init {
        interstitialAd.loadAd(AdRequest.Builder().build())
        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                interstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
    }

    fun show(delegate: AdsDelegate?, persent: Int) {
        if (persent == 100 || generateRandom() < persent) {
            if (interstitialAd.isLoaded) {
                interstitialAd.show()
                interstitialAd.adListener = object : AdListener() {
                    override fun onAdClosed() {
                        interstitialAd.loadAd(AdRequest.Builder().build())
                        delegate?.afterAdsClose()
                    }
                }
            } else {
                delegate?.afterAdsClose()
            }
        } else {
            delegate?.afterAdsClose()

        }

    }

    fun generateRandom(): Int {
        return (1..100).shuffled().first()
    }
}