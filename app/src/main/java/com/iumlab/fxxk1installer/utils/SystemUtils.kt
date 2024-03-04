package com.iumlab.fxxk1installer.utils

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.iumlab.fxxk1installer.MainActivity
import com.iumlab.fxxk1installer.R

object SystemUtils {
    fun setupVisibleInLauncher(context: Context) {
        try {
            val componentName = ComponentName(context, MainActivity::class.java)
            val setting = context.packageManager.getComponentEnabledSetting(componentName)

            val isHidden = setting == PackageManager.COMPONENT_ENABLED_STATE_DISABLED

            when (isHidden) {
                true -> {
                    context.packageManager.setComponentEnabledSetting(
                        componentName,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP
                    )
                    Toast.makeText(context,
                        context.getString(R.string.the_app_icon_is_visible_now), Toast.LENGTH_SHORT).show()
                }

                false -> {
                    context.packageManager.setComponentEnabledSetting(
                        componentName,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                    )
                    Toast.makeText(context,
                        context.getString(R.string.the_icon_is_hidden_now), Toast.LENGTH_SHORT).show()
                    Toast.makeText(context,
                        context.getString(R.string.it_may_not_take_effect_on_some_devices), Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}