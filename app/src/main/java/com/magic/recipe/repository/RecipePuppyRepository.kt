package com.magic.recipe.repository

import com.magic.recipe.api.APIManager
import com.magic.recipe.api.APIResponseListener

open class RecipePuppyRepository {

    /**
     * Get Recipe Puppy List
     */
    fun getRecipePuppy(paramsMap: Map<String, String>,listener: APIResponseListener) {
        val call = APIManager.instance?.defaultAPI?.getRecipePuppy(paramsMap)
        APIManager.instance?.callAPI(call, listener)
    }

    companion object {
        @JvmStatic
        val recipePuppyRepository: RecipePuppyRepository
            get() {
                return RecipePuppyRepository()
            }
    }
}
