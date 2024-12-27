package io.pc7.ninu.presentation.settings.language


import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.domain.mapper.toDisplayString
import io.pc7.ninu.domain.model.util.Language
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.util.changeLanguage
import java.util.Locale


@Composable
fun LanguageSelectionScreen(
    navBack: () -> Unit
){
    val activity = LocalContext.current as Activity
    val locale = Locale.getDefault()
    val currentLocale = remember {
        mutableStateOf(Language.English)
    }
    LaunchedEffect(locale) {
        currentLocale.value = Language.toLanguage(locale.language)
    }
    LanguageSelectionScreen(
        selectedLanguage = currentLocale.value,
        selectLanguage = {
            activity.changeLanguage(it)
        },
        navBack = navBack
    )
}

@Composable
private fun LanguageSelectionScreen(
    selectedLanguage: Language,
    selectLanguage: (Language) -> Unit,
    navBack: () -> Unit,
){
    ScrollableColumn {
        ButtonTopLeftBack(onClick = navBack, text = stringResource(R.string.languages))

        LanguageItem(
            language = Language.English,
            currentLanguage = selectedLanguage,
            onSelect = selectLanguage,
        )
        LanguageItem(
            language = Language.Slovenia,
            currentLanguage = selectedLanguage,
            onSelect = selectLanguage
        )

    }
    
}


@Composable
private fun LanguageItem(
    language: Language,
    currentLanguage: Language,
    onSelect: (Language) -> Unit,

) {
    val selected: Boolean = language == currentLanguage

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable{onSelect(language)}

    ) {
        Text(text = language.toDisplayString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(30.dp)
                .background(if (selected) colorScheme.successDark else colorScheme.custom3D3D3D)

        ){
            Icon(painter = painterResource(id = io.pc7.ninu.R.drawable.icon_check_bold), contentDescription = null,
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
            )
        }



    }


}



@Preview
@Composable
private fun LanguageSelectionScreenPreview(){
    NINUTheme{
        LanguageSelectionScreen(
            selectedLanguage = Language.English,
            selectLanguage = {},
            navBack = {}
        )
    
    }
}



