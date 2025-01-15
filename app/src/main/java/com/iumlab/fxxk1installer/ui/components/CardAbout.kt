package com.iumlab.fxxk1installer.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iumlab.fxxk1installer.R
import com.iumlab.fxxk1installer.utils.SystemUtils

@Preview
@Composable
fun CardAbout() {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    var clickTimes by remember { mutableIntStateOf(0) }
    var clickMoment by remember { mutableLongStateOf(0L) }
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
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.clickable( onClick = {
                    clickMoment = System.currentTimeMillis();
                    clickTimes++
                    if (clickTimes == 7) {
                        SystemUtils.setupVisibleInLauncher(context)
                        clickTimes = 0
                    }
                    if (System.currentTimeMillis() - clickMoment > 2000) {
                        clickTimes = 0
                    }
                    clickMoment = System.currentTimeMillis();
                },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .clickable(
                        onClick = {
                            uriHandler.openUri("https://github.com/Ium-Lab/Apk.1-Installer")
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = false),

                        )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github_fill),
                    contentDescription = "Github"
                )
                Column {
                    Text(
                        text = "Github",
                        modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        text = "Ium-Lab/Apk.1-Installer",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
/*            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .clickable(
                        onClick = {
                            uriHandler.openUri("https://github.com/Ium-Lab/Apk.1-Installer")
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),

                        )
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_github_fill),
                    contentDescription = "Update"
                )
                Text(//https://api.github.com/repos/Ium-Lab/Apk.1-Installer/releases/latest
                    text = "Update",
                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                )
            }*/
        }
    }
}
