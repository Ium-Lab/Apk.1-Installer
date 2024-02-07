package com.iumlab.fxxk1installer

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.alibaba.fastjson.JSON
import com.google.android.material.color.DynamicColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iumlab.fxxk1installer.ui.components.PermissionDialog
import com.iumlab.fxxk1installer.ui.components.setSystemBar
import com.iumlab.fxxk1installer.ui.theme.AppTheme
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


open class InstallActivity : ComponentActivity() {
    private val TAG = this.javaClass.name.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.Transparent.hashCode()
        window.navigationBarColor = Color.Transparent.hashCode()
//        DynamicColors.applyToActivityIfAvailable(this)
        super.onCreate(savedInstanceState)
        checkPermissions()
        setContent {
            AppTheme {
                setSystemBar()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
//                    val b = packageManager.canRequestPackageInstalls()
//                    if (!b) {
//                        PermissionDialog {
//                            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
//                            startActivity(intent)
//                        }
//                    }
                }
            }
        }
        if (intent != null) {
//            checkPermissions()
//            handleShared(intent)
//            Log.e(TAG, JSON.toJSONString(intent))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleShared(intent)
    }

    private fun handleShared(i: Intent?) {
        val intent: Intent? = i ?: intent
        val action: String? = intent?.action
        val type: String? = intent?.type
        val uri: Uri? = intent?.data

        val filePath = uri?.encodedPath ?: return
        if (filePath.endsWith(".apk")){
            install(i)
        } else {
            copyFile(uri)
        }

    }

    private fun copyFile(uri : Uri) {
        try {
            val inputStream: InputStream =
                contentResolver.openInputStream(uri) ?: return
            val path = this.getExternalFilesDir("cache")!!.absolutePath+ "/temp.apk"
            val outputStream: OutputStream = FileOutputStream(path)
            copyStream(inputStream, outputStream, path)
            inputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun copyStream(input: InputStream, output: OutputStream, path: String) { //文件存储
        val BUFFER_SIZE = 1024 * 2
        val buffer = ByteArray(BUFFER_SIZE)
        val in0 = BufferedInputStream(input, BUFFER_SIZE)
        val out = BufferedOutputStream(output, BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (in0.read(buffer, 0, BUFFER_SIZE).also { n = it } != -1) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
            out.close()
            in0.close()
            val id = application.packageName
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = FileProvider.getUriForFile(
                this,
                "$id.provider",
                File(path)
            )
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            install(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun install(apk : File) {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        val contentUri = FileProvider.getUriForFile(this, mAuthority, apk)
//        intent.setDataAndType(contentUri, INTENT_TYPE)
//        mActivity.startActivity(intent)
    }

    private fun install(intent: Intent?) {
        if (intent == null) {
            return
        }
        val dataUri = intent.data
//        val intent = packageManager.getLaunchIntentForPackage("com.android.packageinstaller")!!
        val intent = Intent(Intent.ACTION_VIEW)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(
            Intent.FLAG_GRANT_READ_URI_PERMISSION /*   | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION*/
        )
        intent.setDataAndType(dataUri, "application/vnd.android.package-archive")
        startActivity(intent)
    }

    private fun checkPermissions() {
        val b = packageManager.canRequestPackageInstalls()

        if (b) {
            handleShared(intent)
        } else {

            showDialog()
        }

    }
    private fun showDialog() {
        val builder = MaterialAlertDialogBuilder(this, R.style.AlertDynamicColor)
        builder.setTitle(R.string.get_permission)
        builder.setMessage(R.string.desc_permission)
        builder.setPositiveButton(R.string.ok) { dialog, which ->
            dialog.dismiss()
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
            startActivity(intent)
        }
        builder.setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}
