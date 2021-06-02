package com.mutsanna.attc.networking

import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("predict")
    fun getPost(): Call<ArrayList<PostResponse>>

    @Multipart
    @POST("predict")
    fun createPost(
        @Part image: String?
    ): Call<CreatePostResponse>

}