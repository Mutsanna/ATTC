package com.mutsanna.attc.networking

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("predict")
    fun getPost(): Call<ArrayList<PostResponse>>

    @Multipart
    @POST("predict")
    fun createPost(
        @Part image: MultipartBody.Part
    ): Call<CreatePostResponse>

//    abstract fun createPost(image: String): Call<CreatePostResponse>

}