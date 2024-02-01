package com.iumlab.fxxk1installer

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.iumlab.fxxk1installer.ui.components.PermissionDialog
import com.iumlab.fxxk1installer.ui.components.setSystemBar
import com.iumlab.fxxk1installer.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    private val TAG = this.javaClass.name.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.Transparent.hashCode()
        window.navigationBarColor = Color.Transparent.hashCode()

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                setSystemBar()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Home() {
    AppTheme {
        Column {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(text = stringResource(id = R.string.app_name))
                        }
                    )
                },
            ) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    CardGuide()
                    CardPermission()
                    CardAbout()
                }

            }
        }
    }
}

@Composable
fun CardGuide() {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
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
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.guide),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(id = R.string.app_guide),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(0.dp,10.dp, 0.dp, 0.dp)
            )

            Text(
                text = stringResource(id = R.string.guide_steps),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp),
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}

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
            .clickable {
                popUp.value = true

            }) {
        Row(
            modifier = Modifier.padding(20.dp)
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



@Composable
fun CardAbout() {
    val uriHandler = LocalUriHandler.current
    OutlinedCard(
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
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(R.string.about),
                style = MaterialTheme.typography.headlineLarge
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .clickable(
                        onClick = {
                            uriHandler.openUri("https://github.com/Ium-Lab/Fxxk.1-Installer")
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),

                        )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github_fill),
                    contentDescription = "Github"
                )
                Text(
                    text = "Github",
                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                )
            }
        }
    }
}