package com.yourcompany.bullseye.data.network_clients

import com.yourcompany.bullseye.data.api_services.MockServerApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MockServerNetworkClient {
	private companion object {
		const val BASE_URL =
			"https://b89f41dd-0d8b-4aad-8a12-5ad7b54e842c.mock.pstmn.io"
	}

	private val loggingInterceptor = HttpLoggingInterceptor()
		.apply {
			level = HttpLoggingInterceptor.Level.BODY
		}

	private val client = OkHttpClient.Builder()
		.addInterceptor(loggingInterceptor)
		.build()

	private val retrofit: Retrofit = Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl(BASE_URL)
		.client(client)
		.build()

	val service: MockServerApi by lazy {
		retrofit.create(MockServerApi::class.java)
	}
}