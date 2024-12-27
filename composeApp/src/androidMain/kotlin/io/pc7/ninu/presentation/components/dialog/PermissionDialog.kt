package io.pc7.ninu.presentation.components.dialog

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.util.permission.Permissions
import io.pc7.ninu.presentation.util.permission.getCameraPermissions


@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    permission: Permissions,

){

    val context = LocalContext.current

    NINUDialog(
        onDismiss = onDismiss,
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorScheme.black.copy(alpha = 0.5f),
                                colorScheme.black.copy(alpha = 0.6f)
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Camera Permission",
                        style = MaterialTheme.typography.labelLarge,
                        color = colorScheme.white
                    )

                    Text(
                        text = permission.description,
                        color = colorScheme.white
                    )
                }
                Button(
                    onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.white,
                        contentColor = colorScheme.primary
                    ),
                ) {
                    Text(
                        text = "Go to settings",
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(
                    text = "Close",
                    style = MaterialTheme.typography.labelMedium,
                    color = colorScheme.white,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onDismiss)
                )

            }
        }

    )

}


@Preview(widthDp = 250, heightDp = 300)
@Composable
private fun NINUDialogPreview(){
    NINUTheme{
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = R.drawable.profile_picture), contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            PermissionDialog(
                onDismiss = {},
                permission = getCameraPermissions()[0]
            )
        }


    }
}