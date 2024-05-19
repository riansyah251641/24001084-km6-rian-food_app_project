package com.fromryan.projectfoodapp.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.catnip.kokomputer.tools.MainCoroutineRule
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

class LoginViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(LoginViewModel(repository))
    }

    @Test
    fun doLogin() {
        every { repository.doLogin(any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.doLogin("helloworld@gmail.com", "helloworld")

        verify { repository.doLogin(any(), any()) }
    }
}
