package com.superhapp.fairusers

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.superhapp.fairusers.data.ResultCallback
import com.superhapp.fairusers.view.model.User as ViewUser
import com.superhapp.fairusers.data.model.User
import com.superhapp.fairusers.data.datasource.UsersDataSource
import com.superhapp.fairusers.data.repository.UsersRepository
import com.superhapp.fairusers.view.viewmodel.UsersViewModel
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

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    @Mock
    private lateinit var usersDataSource: UsersDataSource

    @Mock
    private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<ResultCallback<List<User>>>

    private lateinit var viewModel: UsersViewModel
    private lateinit var repository: UsersRepository

    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var onRenderUsersObserver: Observer<List<ViewUser>>

    private lateinit var usersEmptyList: List<User>
    private lateinit var usersList: List<User>

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(context.applicationContext).thenReturn(context)

        repository = UsersRepository(usersDataSource)
        viewModel = UsersViewModel(repository)

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
    fun `retrieve users with ViewModel and Repository returns empty data`() = runBlockingTest {
        with(viewModel) {
            loadUsers()
            users.observeForever(onRenderUsersObserver)
        }

        verify(usersDataSource, times(1)).fetchUsers(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(usersEmptyList)

        Assert.assertTrue(viewModel.users.value?.size == 0)
    }

    @Test
    fun `retrieve users with ViewModel and Repository returns full data`() = runBlockingTest {
        with(viewModel) {
            loadUsers()
            users.observeForever(onRenderUsersObserver)
        }

        verify(usersDataSource, times(1)).fetchUsers(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onSuccess(usersList)

        Assert.assertTrue(viewModel.users.value?.size == 3)
    }

    @Test
    fun `retrieve users with ViewModel and Repository returns an error`() = runBlockingTest {
        with(viewModel) {
            loadUsers()
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(usersDataSource, times(1)).fetchUsers(capture(operationCallbackCaptor))
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers() {
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        onRenderUsersObserver = mock(Observer::class.java) as Observer<List<ViewUser>>
    }

    private fun mockData() {
        usersEmptyList = emptyList()
        val mockList: MutableList<User> = mutableListOf()
        mockList.add(
            User(
                "0",
                "m",
                "Jean",
                "Bernard",
                "jb@mail.fr",
                "",
                null, null, null, null, null, null
            )
        )
        mockList.add(User(
            "1",
            "mr",
            "Camille",
            "Leroix",
            "cl@mail.fr",
            "",
            null, null, null, null, null, null
        ))
        mockList.add(User(
            "0",
            "m",
            "Luis",
            "Casanova",
            "lc@mail.fr",
            "",
            null, null, null, null, null, null
        ))

        usersList = mockList.toList()
    }
}