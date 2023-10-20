package com.yourcompany.bullseye.data.api_services

import com.yourcompany.bullseye.data.models.SaveScoreResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface MockServerApi {
	@POST("score/save")
	suspend fun saveScore(
		@Query("round") round: Int,
		@Query("score") score: Int
	): SaveScoreResponse
}