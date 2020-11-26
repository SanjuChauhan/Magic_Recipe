package com.magic.recipe.model

import com.google.gson.annotations.SerializedName

class RecipePuppyResponse {
    @SerializedName("title")
    var title: String = ""

    @SerializedName("version")
    var version: Double = 0.0

    @SerializedName("href")
    var href: String = ""

    @SerializedName("results")
    var results: List<RecipePuppy> = listOf()
}