package com.yourcompany.bullseye.data.repositories

import android.util.Log
import com.yourcompany.bullseye.data.api_services.MockServerApi

class ScoreRepository(
	private val api: MockServerApi
) {
	suspend fun saveScore(
		round: Int,
		score: Int
	){
		val response = api.saveScore(
			round = round,
			score = score
		)

		Log.i("RETROFIT RESPONSE", response.result)
	}
}