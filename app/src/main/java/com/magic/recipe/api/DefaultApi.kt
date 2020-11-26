package com.magic.recipe.api

import com.magic.recipe.model.RecipePuppyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DefaultApi {

    /**
     * get Recipe Puppy
     */
    @GET("/api")
    fun getRecipePuppy(@QueryMap paramsMap: Map<String, String>): Call<RecipePuppyResponse>
}