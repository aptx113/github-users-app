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
package com.danteyu.studio.githubusersapp.data.source.api

import com.danteyu.studio.githubusersapp.MainCoroutineRule
import com.google.common.truth.Truth
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.IOException
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.nio.charset.StandardCharsets

/**
 * Created by George Yu in Nov. 2021.
 */
@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class GitHubUsersApiServiceTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var service: GitHubUsersApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun initService() {
        mockWebServer = MockWebServer()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        service = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create()
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Throws(IOException::class)
    @Test
    fun getGitHubUserFromNetwork() = runBlocking {
        enqueueResponse("/GitHubUsersResult.json")
        val response = service.getUsers()
        mockWebServer.takeRequest()

        val loaded = response[0]
        Truth.assertThat(loaded.login).isEqualTo("mojombo")
        Truth.assertThat(loaded.id).isEqualTo(1)
        Truth.assertThat(loaded.avatarUrl)
            .isEqualTo("https://avatars.githubusercontent.com/u/1?v=4")
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }
}
