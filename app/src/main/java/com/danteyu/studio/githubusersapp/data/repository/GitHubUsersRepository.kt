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

import com.danteyu.studio.githubusersapp.data.source.api.GitHubUsersApiService
import com.danteyu.studio.githubusersapp.data.source.db.GitHubUsersDao
import com.danteyu.studio.githubusersapp.model.GitHubUser
import com.danteyu.studio.githubusersapp.utils.Resource
import com.danteyu.studio.githubusersapp.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by George Yu in Nov. 2021.
 */
class GitHubUsersRepository @Inject constructor(
    private val apiService: GitHubUsersApiService,
    private val dao: GitHubUsersDao
) :
    Repository {
    override fun getGitHubUsersFlow(): Flow<Resource<List<GitHubUser>>> = flow {
        emit(
            networkBoundResource(
                apiCall = { apiService.getUsers() },
                saveApiCall = { dao.insertGitHubUsers(it) }
            )
        )
    }

    override fun loadGitHubUsersFlow(): Flow<List<GitHubUser>> = dao.loadGitHubUsersFlow()
}
