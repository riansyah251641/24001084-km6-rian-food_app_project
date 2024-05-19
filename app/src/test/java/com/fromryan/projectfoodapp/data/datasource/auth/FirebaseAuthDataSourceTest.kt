package com.fromryan.projectfoodapp.data.datasource.auth

import com.fromryan.projectfoodapp.data.source.firebase.FirebaseService
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseService
    private lateinit var authDataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authDataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val email = "helloworld@gmail.com"
            val password = "helloworld@gmail.com"
            coEvery { service.doLogin(email, password) } returns true
            val data = authDataSource.doLogin(email, password)
            coVerify { service.doLogin(email, password) }
            assertEquals(true, data)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val username = "helloworld"
            val email = "helloworld@gmail.com"
            val password = "helloworld@gmail.com"

            coEvery { service.doRegister(username, email, password) } returns true
            val data = authDataSource.doRegister(username, email, password)
            coVerify { service.doRegister(username, email, password) }
            assertEquals(true, data)
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            coEvery { service.updateProfile(any()) } returns true
            val data = authDataSource.updateProfile()
            coVerify { service.updateProfile(any()) }
            assertEquals(true, data)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            val password = "fuhrer"
            coEvery { service.updatePassword(password) } returns true
            val data = authDataSource.updatePassword(password)
            coVerify { service.updatePassword(password) }
            assertEquals(true, data)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            val email = "helloworld@gmail.com"
            coEvery { service.updateEmail(email) } returns true
            val data = authDataSource.updateEmail(email)
            coVerify { service.updateEmail(email) }
            assertEquals(true, data)
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            every { service.requestChangePasswordByEmail() } returns true
            val data = authDataSource.requestChangePasswordByEmail()
            verify { service.requestChangePasswordByEmail() }
            assertEquals(true, data)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            every { service.doLogout() } returns true
            val data = authDataSource.doLogout()
            verify { service.doLogout() }
            assertEquals(true, data)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { service.isLoggedIn() } returns true
            val data = authDataSource.isLoggedIn()
            verify { service.isLoggedIn() }
            assertEquals(true, data)
        }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val user = mockk<FirebaseUser>()
            every { service.getCurrentUser() } returns user
            every { user.uid } answers { "number" }
            every { user.displayName } answers { "helloworld" }
            every { user.email } answers { "helloworld@gmail.com" }
            every { user.photoUrl } returns mockk(relaxed = true)
            every { user.phoneNumber } returns null

            val data = authDataSource.getCurrentUser()
            verify { service.getCurrentUser() }
            assertEquals("number", data?.id)
            assertEquals("helloworld", data?.fullName)
            assertEquals("helloworld@gmail.com", data?.email)
        }
    }
}
