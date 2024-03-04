package com.iumlab.fxxk1installer.compose

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.iumlab.fxxk1installer.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CardPermission() {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val request = remember { mutableStateOf(false) }
    val popUp = remember { mutableStateOf(false) }
    val isGranted = remember { mutableStateOf(false) }

    if (popUp.value) {
        AlertDialog(
            onDismissRequest = {
                popUp.value = false },
            title = {
                Text(
                    text = stringResource(R.string.get_permission),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.desc_permission),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            dismissButton = {
                TextButton(onClick = {
                    popUp.value = false
                }) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    popUp.value = false
                    request.value = true
                }) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        )
    }
    if (request.value) {
        request.value = false
        popUp.value = false
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
        context.startActivity(intent)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val cardColor = if (isGranted.value)
        MaterialTheme.colorScheme.surface
    else MaterialTheme.colorScheme.errorContainer
    OutlinedCard (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.extraLarge)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.clickable {
                popUp.value = true

            }.padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.get_permission),
                modifier = Modifier.weight(1f, true)
            )
            Icon(Icons.Outlined.KeyboardArrowRight, contentDescription = stringResource(R.string.get_permission))
        }
    }
//    val installPkg = rememberPermissionState(Manifest.permission.REQUEST_INSTALL_PACKAGES)
//    val requestPermissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            // Permission granted
//        } else {
//            // Handle permission denial
//        }
//    }
//    LaunchedEffect(installPkg) {
//        if (!installPkg.status.isGranted && installPkg.status.shouldShowRationale) {
//            // Show rationale if needed
//        } else {
//            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
//        }
//    }



}