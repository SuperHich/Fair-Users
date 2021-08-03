package com.superhapp.fairusers

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.model.User
import com.superhapp.fairusers.model.UserDetailsDataSource
import com.superhapp.fairusers.model.UserDetailsRepository
import com.superhapp.fairusers.viewmodel.UserDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

@ExperimentalCoroutinesApi
class UserDetailsViewModelTest {

    @Mock
    private lateinit var userDetailsDataSource: UserDetailsDataSource

    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<ResultCallback<User>>

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var repository: UserDetailsRepository

    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var onRenderUserObserver: Observer<User>

    private lateinit var userDetails: User

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(context.applicationContext).thenReturn(context)

        repository = UserDetailsRepository(userDetailsDataSource)
        viewModel = UserDetailsViewModel(repository)

        Dispatchers.setMain(testDispatcher)

        mockData()
        setupObservers()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `retrieve user details with ViewModel and Repository returns null data`() = runBlockingTest {
        with(viewModel) {
            fetchUserDetails("")
            userDetails.observeForever(onRenderUserObserver)
        }

        verify(userDetailsDataSource, times(1)).fetchUserDetails(anyString(), capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(null)

        Assert.assertTrue(viewModel.userDetails.value == null)
    }

    @Test
    fun `retrieve user details with ViewModel and Repository returns data`() = runBlockingTest {
        with(viewModel) {
            fetchUserDetails("0")
            userDetails.observeForever(onRenderUserObserver)
        }

        verify(userDetailsDataSource, times(1)).fetchUserDetails(anyString(), capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(userDetails)

        Assert.assertTrue(viewModel.userDetails.value != null)
    }

    @Test
    fun `retrieve user details with ViewModel and Repository returns an error`() = runBlockingTest {
        with(viewModel) {
            fetchUserDetails("")
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(userDetailsDataSource, times(1)).fetchUserDetails(anyString(), capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers() {
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        onRenderUserObserver = mock(Observer::class.java) as Observer<User>
    }

    private fun mockData() {
        userDetails = User(
            "0",
            "m",
            "Jean",
            "Bernard",
            "jb@mail.fr",
            "",
            Date(),
            null,
            null,
            "+33611223344",
            Date(),
            Date()
        )
    }
}