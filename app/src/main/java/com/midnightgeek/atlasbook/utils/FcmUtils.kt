package com.midnightgeek.atlasbook.utils

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import javax.inject.Inject

/**
 * <p>Firebase Handler</p>
 * This class used to handle firebase token
 */
class FcmUtils @Inject constructor(var prefHandler: PrefHandler) {
    val TAG = "FcmUtils"

    fun checkFcmToken() {
        Log.e(TAG, "checkFcmToken")
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                Log.e(TAG, "onComplete")
                if (!task.isSuccessful) {
                    Log.e(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result.token
                Log.e(TAG, "token is  :$token")
                if (token != readFcmToken() || !getValidation()) {
                    Log.e(
                        TAG,
                        "token is different !!!" + "=> old :" + readFcmToken() + " , new => " + token
                    )
                    setValidation(false)
                    sendFcmToken(token)
                } else {
                    Log.e(TAG, "token is same =>$token")
                }
            })
    }

    fun sendFcmToken(token: String) {
        Log.e(TAG, "sendFcmToken => $token")
    }

    fun readFcmToken(): String? {
        return prefHandler.getString(prefHandler.FCM_TOKEN, "")
    }

    fun writeFcmToken(fcmToken: String?) {
        prefHandler.setPreference(prefHandler.FCM_TOKEN, fcmToken)
    }

    fun setValidation(isValid: Boolean) {
        prefHandler.setPreference(prefHandler.FCM_TOKEN_IS_VALID, isValid)
    }

    fun getValidation(): Boolean {
        return prefHandler.getBoolean(prefHandler.FCM_TOKEN_IS_VALID, false)
    }

}