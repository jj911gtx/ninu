package io.pc7.ninu.presentation.components.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.pc7.ninu.domain.model.util.Country
import io.pc7.ninu.presentation.register.userInfo.UserInfoInputAction


@Composable
fun CountriesSelectModalBottomSheet(
    onDismiss: () -> Unit,
    selectedCountry: Country?,
    countries: Array<Country>,
    onSelectCountry: (Country) -> Unit,
) {
    NINUModalSheet(
        onDismiss = onDismiss,
        onConfirm = null,
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(countries){ country ->
                NINUModalBottomSheetItem(
                    text = country.name,
                    onClick = {
                        onSelectCountry(country)
                        onDismiss()
                    },
                    selected = country == selectedCountry

                )

            }
        }
    }
}

