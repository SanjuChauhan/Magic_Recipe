package com.magic.recipe.api

interface APIResponseListener {
    fun onSuccess(response: Any?)
    fun onFail(message: String?)
}