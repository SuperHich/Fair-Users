package com.superhapp.fairusers.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.superhapp.fairusers.R
import com.superhapp.fairusers.di.UserDetailsInjection
import com.superhapp.fairusers.model.User
import com.superhapp.fairusers.viewmodel.UserDetailsViewModel
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.layout_error.*

class UserDetailsActivity : AppCompatActivity() {
    companion object {
        const val TAG = "UserDetailsActivity"
        const val USER_ID = "USER_ID"
    }

    private lateinit var viewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        val userId = intent.extras?.getString(USER_ID)
        viewModel.fetchUserDetails(userId ?: "")
        progressBar.visibility = View.VISIBLE
    }

    private fun displayUser(user: User) {
        ("${user.title.capitalize()} ${user.firstName.capitalize()} ${user.lastName.capitalize()}" +
                "\n${user.dateOfBirth}" +
                "\n${user.email}" +
                "\n${user.phone}" +
                "\n${user.location}").also { textViewInfos.text = it }
        Picasso.get().load(user.picture).into(imageView)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            UserDetailsInjection.provideViewModelFactory(this)
        ).get(UserDetailsViewModel::class.java)

        viewModel.userDetails.observe(this, handleUserDetails)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
    }

    private val handleUserDetails = Observer<User> {
        Log.v(TAG, "data updated $it")
        progressBar.visibility = View.GONE
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        displayUser(it)
    }

    private val onMessageErrorObserver = Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        progressBar.visibility = View.GONE
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = "Error $it"
    }
}