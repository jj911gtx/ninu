package io.pc7.ninu.presentation.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun SuccessScreen(
    navNext: () -> Unit,
    buttonText: String= stringResource(R.string.start_using),
) {

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val offsetYInDp = with(LocalDensity.current) { (constraints.maxHeight * 0.2f).toDp() }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = offsetYInDp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_party_popper),
                contentDescription = null,
                tint = colorScheme.secondaryDark2,
                modifier = Modifier
                    .size(70.dp)
            )
            Spacer(modifier = Modifier.heightIn(10.dp))

            Text(
                text = stringResource(R.string.yay_success),
                style = MaterialTheme.typography.headlineLarge,
                color = colorScheme.white
            )

            Text(
                text = "Description",
                style = MaterialTheme.typography.titleLarge,
                color = colorScheme.white.copy(alpha = 0.7f)
            )
        }

        DefaultButtonText(
            onClick = navNext,
            text = buttonText,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }

}


@Preview(widthDp = 500, heightDp = 900)
@Composable
private fun RegistrationCompletedScreenPreview() {
    NINUTheme {
        SuccessScreen({})
    }
}