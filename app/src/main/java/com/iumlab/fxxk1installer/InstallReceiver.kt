package com.iumlab.fxxk1installer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import android.util.Log
import java.io.File


class InstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val pm: PackageManager = context.packageManager

        if (TextUtils.equals(intent.action, Intent.ACTION_PACKAGE_ADDED)) {
            val packageName: String = intent.data!!.schemeSpecificPart
            val path = context.getExternalFilesDir("cache")!!.absolutePath+ "/temp.apk"
            val file = File(path)
            file.delete()
        }
/*        else if (TextUtils.equals(intent.action, Intent.ACTION_PACKAGE_REPLACED)) {
            val packageName: String = intent.data!!.schemeSpecificPart
        } else if (TextUtils.equals(intent.action, Intent.ACTION_PACKAGE_REMOVED)) {
            val packageName: String = intent.data!!.schemeSpecificPart
        }*/
    }
}