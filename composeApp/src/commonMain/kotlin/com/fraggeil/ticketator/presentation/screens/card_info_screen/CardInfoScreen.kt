package com.fraggeil.ticketator.presentation.screens.card_info_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CardInfoScreenRoot(
    viewModel: CardInfoViewModel,
    navigateBack: () -> Unit,
    navigateToEnterCode: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CardInfoScreen(
        state = state,
        onAction = {
            when (it) {
                CardInfoAction.OnBackClicked -> navigateBack()
                CardInfoAction.OnNextClicked -> navigateToEnterCode()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun CardInfoScreen(
    state: CardInfoState,
    onAction: (CardInfoAction) -> Unit,
) {

}