package com.amanda.githubrepos.model

import app.cash.turbine.test
import com.amanda.githubrepos.data.User
import com.amanda.githubrepos.data.UserReposItem
import com.amanda.githubrepos.utils.Status
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.time.ExperimentalTime


class RepoListViewModelTest {

    @Mock
    private lateinit var repository: GithubDataRepository

    private lateinit var viewModel: RepoListViewModel

    private val expectedUser = User("http://avatar", "Name")

    private val expectedRepos = repoList()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RepoListViewModel(repository)
    }

    @ExperimentalTime
    @Test
    fun `when user is called, then loading state is emitted`() {
        repository.stub { onBlocking { user(any()) } doReturn expectedUser}
        runBlockingTest {
            viewModel.user("username").test {
                expectItem().status shouldBe Status.LOADING
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when user is called, given repository returns successfully, then success is emitted`() {
        repository.stub { onBlocking { user(any()) } doReturn expectedUser}
        runBlockingTest {
            viewModel.user("username").test {
                expectItem().status shouldBe Status.LOADING
                expectItem().status shouldBe Status.SUCCESS
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when user is called, given repository returns successfully, then user object is emitted`() {
        repository.stub { onBlocking { user(any()) } doReturn expectedUser}
        runBlockingTest {
            viewModel.user("username").test {
                expectItem().status shouldBe Status.LOADING
                expectItem().data shouldBe expectedUser
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when user is called, given repository fails, then error state is emitted`() {
        repository.stub { onBlocking { user(any()) } doAnswer { throw Exception() }}
        runBlockingTest {
            viewModel.user("username").test {
                expectItem().status shouldBe Status.LOADING
                expectItem().status shouldBe Status.ERROR
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when user is called, given repository fails, then no user is emitted`() {
        repository.stub { onBlocking { user(any()) } doAnswer { throw Exception() }}
        runBlockingTest {
            viewModel.user("username").test {
                expectItem().status shouldBe Status.LOADING
                expectItem().data shouldBe null
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when repos is called, then loading state is emitted`() {
        repository.stub { onBlocking { repos(any()) } doReturn expectedRepos}
        runBlockingTest {
            viewModel.repos("username").test {
                expectItem().status shouldBe Status.LOADING
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when repos is called, given repository returns successfully, then success is emitted`() {
        repository.stub { onBlocking { repos(any()) } doReturn expectedRepos}
        runBlockingTest {
            viewModel.repos("username").test {
                expectItem().status shouldBe Status.LOADING
                expectItem().status shouldBe Status.SUCCESS
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when repos is called, given repository returns successfully, then repos list is emitted`() {
        repository.stub { onBlocking { repos(any()) } doReturn expectedRepos}
        runBlockingTest {
            viewModel.repos("username").test {
                expectItem().status shouldBe Status.LOADING
                expectItem().data shouldBe expectedRepos
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when repos is called, given repository fails, then error state is emitted`() {
        repository.stub { onBlocking { repos(any()) } doAnswer { throw Exception() }}
        runBlockingTest {
            viewModel.repos("username").test {
                expectItem().status shouldBe Status.LOADING
                expectItem().status shouldBe Status.ERROR
                expectComplete()
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `when repos is called, given repository fails, then no github repos are emitted`() {
        repository.stub { onBlocking { repos(any()) } doAnswer { throw Exception() }}
        runBlockingTest {
            viewModel.repos("username").test {
                expectItem().status shouldBe Status.LOADING
                expectItem().data shouldBe null
                expectComplete()
            }
        }
    }

    private fun repoList() = listOf<UserReposItem>(
        UserReposItem("my first repo", 0, "first", 0, "JAN-04-2021"),
        UserReposItem("my second repo", 10, "second", 5, "JAN-05-2021"),
    )
}