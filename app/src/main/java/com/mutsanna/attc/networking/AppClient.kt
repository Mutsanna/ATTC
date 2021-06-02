package com.mutsanna.attc.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppClient {
    private const val BASE_URL = "https://bangkit-ml-deployment-mxcyue5puq-et.a.run.app/"

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
}