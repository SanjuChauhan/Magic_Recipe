package com.magic.recipe.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magic.recipe.R
import com.magic.recipe.constants.INTENT_EXTRA_DATA
import com.magic.recipe.databinding.ActivityMainBinding
import com.magic.recipe.model.RecipePuppy
import com.magic.recipe.ui.adapter.RecipePuppyRvAdapter
import com.magic.recipe.util.GsonUtils
import com.magic.recipe.viewmodel.RecipePuppyModel
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recipePuppyModel: RecipePuppyModel
    private var recipePuppyRvAdapter: RecipePuppyRvAdapter? = null
    var isLoadMoreFeedPeopleListData: Boolean = true
    var isFromSearch: Boolean = false
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this
        binding.lifecycleOwner = this

        recipePuppyModel = ViewModelProvider(this).get(RecipePuppyModel::class.java)
        binding.recipePuppyModel = recipePuppyModel

        initObserver()
        initAdapter()
        getRecipePuppy()
    }

    /***
     * This method is use to initialize observer
     */
    private fun initObserver() {
        recipePuppyModel.strToastMessage.observe(this, Observer { message -> showToast(message) })

        recipePuppyModel.recipePuppyListMutableData.observe(this, Observer {
            Timber.e("Size : ${it.size}")
            if (isFromSearch) {
                recipePuppyRvAdapter?.clear()
            }
            if (it.isNotEmpty()) {
                recipePuppyRvAdapter?.addAll(it)
            }
            isLoadMoreFeedPeopleListData = true
        })

        recipePuppyModel.searchText.observe(this, Observer {
            Timber.e("Size : $it")
            if (it.length >= 3) {
                isFromSearch = true
                getRecipePuppy(it)
            }
        })
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvRecipePuppy.layoutManager = linearLayoutManager

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider)!!)
        binding.rvRecipePuppy.addItemDecoration(itemDecorator)

        recipePuppyRvAdapter = RecipePuppyRvAdapter(this)
        binding.rvRecipePuppy.adapter = recipePuppyRvAdapter
        recipePuppyRvAdapter?.setItemClickListener(object :
            RecipePuppyRvAdapter.OnItemClickListener {
            override fun onItemClick(data: RecipePuppy, position: Int) {
                onNavigateToNewsSource(data)
            }
        })
        setUpPaginationScroll(linearLayoutManager)
    }

    private fun setUpPaginationScroll(linearLayoutManager: LinearLayoutManager) {
        binding.rvRecipePuppy.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount: Int = linearLayoutManager.childCount
                val totalItemCount: Int = linearLayoutManager.itemCount
                val firstVisibleItemPosition: Int =
                    linearLayoutManager.findFirstVisibleItemPosition()

                if (isLoadMoreFeedPeopleListData && visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                    Timber.e("Call api for new data")
                    isLoadMoreFeedPeopleListData = false
                    isFromSearch = false
                    currentPage += 1
                    getRecipePuppy(recipePuppyModel.searchText.value!!, currentPage)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    /**
     * Get Recipe Puppy
     */
    private fun getRecipePuppy(searchString: String = "", page: Int = 1) {
        if (checkNetworkState()) {
            recipePuppyModel.callRecipePuppyAPI(searchString, page)
        } else {
            recipePuppyModel.strToastMessage.postValue(getString(R.string.msg_no_internet))
        }
    }

    private fun onNavigateToNewsSource(data: RecipePuppy) {
        val intent = Intent(this, RecipePuppyDetailsActivity::class.java)
        intent.putExtra(INTENT_EXTRA_DATA, GsonUtils.gson?.toJson(data))
        startActivity(intent)
    }
}