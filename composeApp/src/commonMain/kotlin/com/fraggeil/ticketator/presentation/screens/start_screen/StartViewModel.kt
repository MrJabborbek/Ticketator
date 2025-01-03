package com.fraggeil.ticketator.presentation.screens.start_screen

import androidx.lifecycle.ViewModel

class StartViewModel: ViewModel() {
    fun onAction(action: StartAction){
        when(action) {
            is StartAction.OnStartClick -> {
                //TODO save
            }
        }
    }
}