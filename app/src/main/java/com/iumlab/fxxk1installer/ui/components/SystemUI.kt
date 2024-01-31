package com.iumlab.fxxk1installer.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun setSystemBar(useDarkTheme: Boolean = isSystemInDarkTheme()){
    val systemUiController = rememberSystemUiController()
    if(useDarkTheme){
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colorScheme.background,
            darkIcons = false
        )
    }else{
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colorScheme.background,
            darkIcons = true
        )
    }
}