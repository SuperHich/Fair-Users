package com.superhapp.fairusers.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.superhapp.fairusers.R
import com.superhapp.fairusers.di.UsersInjection
import com.superhapp.fairusers.model.User
import com.superhapp.fairusers.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.layout_error.*

class UsersActivity : AppCompatActivity() {
    companion object {
        const val TAG = "UsersActivity"
    }

    private lateinit var viewModel: UsersViewModel
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        setupViewModel()
        setupUI()

        viewModel.loadUsers()
        progressBar.visibility = View.VISIBLE
    }

    private fun setupUI() {
        adapter = UsersAdapter(viewModel.users.value ?: emptyList(), object : UsersAdapter.Listener{
            override fun onItemClicked(userId: String) {
                val intent = Intent(this@UsersActivity, UserDetailsActivity::class.java).apply {
                    putExtra(UserDetailsActivity.USER_ID, userId)
                }
                startActivity(intent)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            UsersInjection.provideViewModelFactory(this)
        ).get(UsersViewModel::class.java)

        viewModel.users.observe(this, handleUsers)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
    }

    private val handleUsers = Observer<List<User>> {
        Log.v(TAG, "data updated $it")
        progressBar.visibility = View.GONE
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.update(it)
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        progressBar.visibility = View.GONE
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }
}