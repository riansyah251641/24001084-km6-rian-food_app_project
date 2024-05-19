package com.fromryan.projectfoodapp.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.catnip.kokomputer.tools.MainCoroutineRule
import com.catnip.kokomputer.tools.getOrAwaitValue
import com.fromryan.projectfoodapp.data.model.User
import com.fromryan.projectfoodapp.data.repository.UserRepository
import com.fromryan.projectfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(repository))
    }

    @Test
    fun changeEditMode() {
        assertFalse(viewModel.isEditMode.value ?: true)
    }

    @Test
    fun changeProfile() {
        val name = "hello World"
        every { repository.updateProfile(name) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        val result = viewModel.changeProfile(name).getOrAwaitValue()
        val payload = (result as ResultWrapper.Success).payload
        assertEquals(true, payload)
    }

    @Test
    fun changePassword() {
        every { repository.requestChangePasswordByEmail() } returns true
        val result = viewModel.requestChangePasswordByEmail()
        assertEquals(true, result)
        verify { repository.requestChangePasswordByEmail() }
    }

    @Test
    fun getCurrentUser() {
        val user =
            User(
                "1",
                "name1",
                "helloworld@gmail.com",
            )
        every { repository.getCurrentUser() } returns user
        val result = viewModel.getCurrentUser()
        assertEquals(user, result)
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { repository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isUserLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
