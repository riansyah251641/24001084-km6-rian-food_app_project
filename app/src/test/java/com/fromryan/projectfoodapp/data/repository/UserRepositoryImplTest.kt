package com.fromryan.projectfoodapp.data.repository

import app.cash.turbine.test
import com.fromryan.projectfoodapp.data.datasource.auth.AuthDataSource
import com.fromryan.projectfoodapp.data.model.User
import com.fromryan.projectfoodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class UserRepositoryImplTest {
    @MockK
    lateinit var dataSource: AuthDataSource
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(dataSource)
    }

    @Test
    fun loginSuccess() {
        val email = "helloworld@gmail.com"
        val password = "helloworld"
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            repository.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun loginError() {
        val email = "helloworld@gmail.com"
        val password = "helloworld"
        coEvery { dataSource.doLogin(any(), any()) } throws IOException("Login failed")
        runTest {
            repository.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun registerSuccess() {
        val username = "helloworld"
        val email = "helloworld@gmail.com"
        val password = "helloworld"

        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repository.doRegister(username, email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun registerLoading() {
        val username = "helloworld"
        val email = "helloworld@gmail.com"
        val password = "helloworld"

        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repository.doRegister(username, email, password).map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun RegisterError() {
        val username = "helloworld"
        val email = "helloworld@gmail.com"
        val password = "helloworld"

        coEvery { dataSource.doRegister(any(), any(), any()) } throws IOException("Register failed")
        runTest {
            repository.doRegister(username, email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun updateProfileSuccess() {
        val username = "helloworld"

        coEvery { dataSource.updateProfile(any()) } returns true
        runTest {
            repository.updateProfile(username).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { dataSource.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updateProfileError() {
        val username = "helloworld"

        coEvery { dataSource.updateProfile(any()) } throws IOException("update Profile error")
        runTest {
            repository.updateProfile(username).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.updateProfile(any()) }
            }
        }
    }

    @Test
    fun updatePasswordSuccess() {
        val password = "hello1234"

        coEvery { dataSource.updatePassword(any()) } returns true
        runTest {
            repository.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun updatePasswordError() {
        val password = "hello1234"

        coEvery { dataSource.updatePassword(any()) } throws IOException("Update password error")
        runTest {
            repository.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun updateEmailSuccess() {
        val email = "komang@gmail.com"

        coEvery { dataSource.updateEmail(any()) } returns true
        runTest {
            repository.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun updateEmail_loading() {
        val email = "komang@gmail.com"

        coEvery { dataSource.updateEmail(any()) } returns true
        runTest {
            repository.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(111)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun updateEmail_error() {
        val email = "komang@gmail.com"

        coEvery { dataSource.updateEmail(any()) } throws IOException("update Email error")
        runTest {
            repository.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(201)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun changePasswordSuccess() {
        runTest {
            coEvery { dataSource.requestChangePasswordByEmail() } returns true
            val result = repository.requestChangePasswordByEmail()
            coVerify { dataSource.requestChangePasswordByEmail() }
            assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            every { dataSource.doLogout() } returns true
            val actualResult = repository.doLogout()
            verify { dataSource.doLogout() }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { dataSource.isLoggedIn() } returns true
            val actualResult = repository.isLoggedIn()
            verify { dataSource.isLoggedIn() }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val currentUser = mockk<User>()
            every { currentUser.id } returns "123"
            every { currentUser.fullName } returns "Komang"
            every { currentUser.email } returns "komang@gmail.com"
            every { dataSource.getCurrentUser() } returns currentUser

            val result = dataSource.getCurrentUser()
            assertEquals("123", result?.id)
            assertEquals("Komang", result?.fullName)
            assertEquals("komang@gmail.com", result?.email)
            verify { repository.getCurrentUser() }
        }
    }
}
