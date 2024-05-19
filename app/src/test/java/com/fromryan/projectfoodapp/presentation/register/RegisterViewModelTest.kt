package com.fromryan.projectfoodapp.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.catnip.kokomputer.tools.MainCoroutineRule
import com.catnip.kokomputer.tools.getOrAwaitValue
import com.fromryan.projectfoodapp.data.repository.UserRepository
import com.fromryan.projectfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(RegisterViewModel(repository))
    }

    @Test
    fun doRegister() {
        val username = "helloworld"
        val email = "helloworld@gmail.com"
        val password = "helloworld"
        every { repository.doRegister(username, email, password) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        val result = viewModel.doRegister(username, email, password).getOrAwaitValue()
        val payload = (result as ResultWrapper.Success).payload
        assertEquals(true, payload)
    }
}
