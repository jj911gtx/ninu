package io.pc7.ninu.presentation.lab

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.theme.custom.colorScheme
import org.koin.androidx.compose.koinViewModel


//@Composable
//fun LabSaveMidPointScreen(
//
//) {
//
//    LabSaveMidPointScreen(
//
//    )
//
//}

@Composable
fun LabSaveMidPointScreen(
    navToSave: () -> Unit,
    navBack: () -> Unit,
    navToHome: () -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()

    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.icon_party_popper),
            contentDescription = null,
            tint = colorScheme.secondaryDark2,
            modifier = Modifier
                .size(70.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.perfume_ready),
            style = MaterialTheme.typography.headlineLarge,
            color = colorScheme.white
        )

        Text(
            text = stringResource(R.string.just_spray_from_your_NINU),
            style = MaterialTheme.typography.titleLarge,
            color = colorScheme.white.copy(alpha = 0.7f)
        )


        Spacer(modifier = Modifier.weight(1f))
        DefaultButtonText(onClick = navToSave, text = stringResource(R.string.save))
        DefaultButtonText(onClick = navBack, text = stringResource(R.string.try_another_one))
        DefaultButtonText(onClick = navToHome, text = stringResource(R.string.back_to_home))


    }


}


@Preview
@Composable
private fun LabSaveMidPointScreenPreview() {
    NINUTheme {
        LabSaveMidPointScreen(
            navToSave = {},
            navBack = {},
            navToHome = {}

        )
    }

}