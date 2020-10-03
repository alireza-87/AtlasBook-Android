package com.midnightgeek.atlasbook.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.midnightgeek.atlasbook.utils.PrefHandler
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val prefHandler: PrefHandler) : ViewModel() {
    val onClick = MutableLiveData<Int>()

    fun onClick(key: Int) {
        if (key == 1) {
            prefHandler.setPreference(prefHandler.TAG_IS_LOGIN, true)
            prefHandler.setPreference(prefHandler.TAG_IS_GUEST, true)
            onClick.value = 1
        } else if (key == 2) {
            onClick.value = 2
        }
    }

    fun login(token: String, name: String) {
        prefHandler.setPreference(prefHandler.FCM_TOKEN, token)
        prefHandler.setPreference(prefHandler.TAG_USER_NAME, name)
    }
}