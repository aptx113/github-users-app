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
package com.danteyu.studio.githubusersapp

import com.danteyu.studio.githubusersapp.databinding.ItemMainOneBinding
import com.danteyu.studio.githubusersapp.model.GitHubUser

/**
 * Created by George Yu in Nov. 2021.
 */
class MainOneViewHolder(viewDataBinding: ItemMainOneBinding) :
    MainViewHolder<ItemMainOneBinding>(viewDataBinding) {

    override fun doBind(gitHubUser: GitHubUser) {
        viewDataBinding.setVariable(BR.user, gitHubUser)
    }
}
