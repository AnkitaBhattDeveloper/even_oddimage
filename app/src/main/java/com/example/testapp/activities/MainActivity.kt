package com.example.testapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.adapter.UsersAdapter
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.repository.ItemRepository
import com.example.testapp.response.User
import com.example.testapp.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var repo: ItemRepository
    lateinit var context: Context
    val itemList: ArrayList<User> = arrayListOf()
    lateinit var usersAdapter: UsersAdapter

    var count: Int = 0
    var isLoaded = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repo = (application as App).itemRepository
        context = this
        usersAdapter = UsersAdapter(context = context, list = itemList)
        val gridlayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        with(binding.itemRecyclerView)
        {
            adapter = usersAdapter
            layoutManager = gridlayoutManager
            hasFixedSize()

        }
        userItems(count.toString())
        loadPage()

    }

    private fun userItems(offset: String) {
        repo.items(offset = offset)
        repo.dataResponse.observe(this@MainActivity)
        {
            it.let {
                if (it.data.users.isNotEmpty()) {
                    isLoaded = false
                    itemList.addAll(it.data.users)
                    usersAdapter.notifyDataSetChanged()

                }
            }
        }
    }

    private fun loadPage() {
        binding.itemRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE
                    && !isLoaded
                ) {
                    isLoaded = true
                    binding.itemRecyclerView.smoothScrollToPosition(itemList.size - 1)
                    if (itemList.size != 0) {
                        count += 10
                        CoroutineScope(Dispatchers.IO).launch {
                            repo.items(count.toString())
                        }
                    }
                }
            }
        })
    }


}
