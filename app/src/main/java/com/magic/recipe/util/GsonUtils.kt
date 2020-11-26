package com.magic.recipe.util

import com.google.gson.Gson

object GsonUtils {
    var gson: Gson? = null
        get() {
            if (field == null) {
                field = Gson()
            }
            return field
        }
        private set
}