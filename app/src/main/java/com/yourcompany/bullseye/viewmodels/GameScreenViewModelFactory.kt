package com.yourcompany.bullseye.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yourcompany.bullseye.data.network_clients.MockServerNetworkClient
import com.yourcompany.bullseye.data.repositories.ScoreRepository


class GameScreenViewModelFactory: ViewModelProvider.Factory {
	override fun <T: ViewModel> create(modelClass: Class<T>): T {
		val client = MockServerNetworkClient()
		val repository = ScoreRepository(client.service)

		return GameScreenViewModel(
			repository = repository
		) as T
	}
}