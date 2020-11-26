package com.magic.recipe.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.magic.recipe.R
import com.magic.recipe.constants.INTENT_EXTRA_DATA
import com.magic.recipe.databinding.ActivityRecipePuppyDetailsBinding
import com.magic.recipe.model.RecipePuppy
import com.magic.recipe.util.GsonUtils

class RecipePuppyDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityRecipePuppyDetailsBinding
    private var recipePuppy: RecipePuppy? = RecipePuppy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_puppy_details)
        binding.lifecycleOwner = this

        binding.ivBack.setOnClickListener {
            finish()
        }

        manageIntent()
    }

    private fun manageIntent() {
        val intent = intent
        if (intent != null) {
            recipePuppy = GsonUtils.gson?.fromJson(
                intent.getStringExtra(INTENT_EXTRA_DATA),
                RecipePuppy::class.java
            )
            showRecipeData()
        }
    }

    private fun showRecipeData() {
        Glide.with(this)
            .load(recipePuppy?.thumbnail)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .centerCrop()
            .into(binding.ivImage)

        binding.tvTitle.text = recipePuppy?.title
        binding.tvDescription.text = recipePuppy?.ingredients
    }
}