package com.iumlab.fxxk1installer.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.iumlab.fxxk1installer.R

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