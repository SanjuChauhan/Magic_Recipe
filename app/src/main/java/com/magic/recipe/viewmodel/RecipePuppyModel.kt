package com.magic.recipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.magic.recipe.api.APIResponseListener
import com.magic.recipe.model.RecipePuppy
import com.magic.recipe.model.RecipePuppyResponse
import com.magic.recipe.repository.RecipePuppyRepository.Companion.recipePuppyRepository

class RecipePuppyModel(application: Application) : AndroidViewModel(application) {

    val strToastMessage = MutableLiveData<String>()
    var searchText = MutableLiveData<String>("")
    val isShowProgressDialog = MutableLiveData<Boolean>()
    val recipePuppyListMutableData = MutableLiveData<List<RecipePuppy>>()

    /**
     * Call Recipe Puppy API.
     */
    fun callRecipePuppyAPI(searchString: String, page: Int) {
        isShowProgressDialog.postValue(true)

        val paramsMap: MutableMap<String, String> = HashMap()
        if (searchString.isNotEmpty()) {
            paramsMap["q"] = searchString
        }
        paramsMap["p"] = page.toString()

        recipePuppyRepository.getRecipePuppy(paramsMap, object : APIResponseListener {
            override fun onSuccess(response: Any?) {
                val recipePuppyResponse = response as RecipePuppyResponse
                isShowProgressDialog.postValue(false)
                recipePuppyListMutableData.postValue(recipePuppyResponse.results)
            }

            override fun onFail(message: String?) {
                strToastMessage.postValue(message)
                isShowProgressDialog.postValue(false)
            }
        })
    }
}