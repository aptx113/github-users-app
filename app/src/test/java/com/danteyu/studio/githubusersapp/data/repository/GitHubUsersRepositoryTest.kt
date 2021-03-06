/*
 * Copyright 2021 Dante Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.danteyu.studio.githubusersapp.data.repository

import com.danteyu.studio.githubusersapp.MainCoroutineRule
import com.danteyu.studio.githubusersapp.data.mock.mockGitHubUsers
import com.danteyu.studio.githubusersapp.data.source.api.GitHubUsersApiService
import com.danteyu.studio.githubusersapp.data.source.db.GitHubUsersDao
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by George Yu in Nov. 2021.
 */
@ExperimentalCoroutinesApi
class GitHubUsersRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: GitHubUsersRepository
    private lateinit var dao: GitHubUsersDao
    private lateinit var apiService: GitHubUsersApiService

    @Before
    fun setup() {
        dao = mockk()
        apiService = mockk()
        repository = GitHubUsersRepository(apiService, dao)
    }

    @Test
    fun loadGitHubUsersFlow_returnFlowOfGitHubUsers() = runBlockingTest {
        val gitHubUsersFlow = flowOf(mockGitHubUsers)
        every { dao.loadGitHubUsersFlow() }.returns(gitHubUsersFlow)
        repository.loadGitHubUsersFlow().collect { result ->
            Truth.assertThat(result).isEqualTo(
                mockGitHubUsers
            )
        }
    }
}
