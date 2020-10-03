package com.midnightgeek.atlasbook.utils

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.midnightgeek.atlasbook.R
import com.midnightgeek.atlasbook.models.ModelUser
import javax.inject.Inject

class Authonticator @Inject constructor(val application: Application,val prefHandler: PrefHandler){
    private lateinit var auth: FirebaseAuth
    init {
    }

    fun isLogin():Boolean{
        return FirebaseAuth.getInstance().currentUser!=null || prefHandler.getBoolean(prefHandler.TAG_IS_GUEST,false)
    }

    fun isGoogleLogin():Boolean{
        return FirebaseAuth.getInstance().currentUser!=null
    }

    fun getUser():ModelUser{
        var modelUser=ModelUser()
        if (isGoogleLogin()){
            val firebaseUser=getGoogleUser()
            modelUser.displayName=firebaseUser?.displayName
            modelUser.avatar=firebaseUser?.photoUrl.toString()
            modelUser.email=firebaseUser?.email
        }else{
            modelUser.displayName=application.getString(R.string.guest)
            modelUser.avatar=""
            modelUser.email=""
        }
        return modelUser
    }

    private fun getGoogleUser():FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
    }
}