package com.magic.recipe.model

import com.google.gson.annotations.SerializedName

class RecipePuppy() {
    @SerializedName("title")
    var title: String = ""

    @SerializedName("href")
    var href: String = ""

    @SerializedName("ingredients")
    var ingredients: String = ""

    @SerializedName("thumbnail")
    var thumbnail: String = ""
}