package io.pc7.ninu.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class EditFavouritesViewModelAndroid(

): ViewModel() {

    val viewModel = EditFavouritesViewModel(
        viewModelScope
    )

}