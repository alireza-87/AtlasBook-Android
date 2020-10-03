package com.midnightgeek.atlasbook.utils

import android.app.Application
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

/**
 * <p>Storage Handler</p>
 * This class used to handle Storage like get root directory address
 */

class StorageHandler @Inject constructor(var application: Application) {
    private val bookPath = File(application.getExternalFilesDir(null).toString() + "/books")
    private val avatarPath = File(application.getExternalFilesDir(null).toString() + "/books")

    init {
        if (!bookPath.exists()) {
            bookPath.mkdir()
        }

        if (!avatarPath.exists()) {
            avatarPath.mkdir()
        }
    }

    fun getBookPath(): String {
        return bookPath.absolutePath
    }

    fun getavaatarPath(): String {
        return avatarPath.absolutePath
    }

    fun generateFileName(name: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(name.toByteArray())).toString(16).padStart(32, '0') + ".pdf"

    }

    fun openFile(path: String) {
        val myIntent = Intent(Intent.ACTION_VIEW)
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val apkURI = FileProvider.getUriForFile(
            application, application.applicationContext
                .packageName.toString() + ".provider", File(path)
        )
        myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        myIntent.data = apkURI

        val chooserIntent = Intent.createChooser(myIntent, "Choose an application to open with:")
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        application.startActivity(chooserIntent)

    }

    fun deleteFile(path: String) {
        File(path).delete()
    }


}