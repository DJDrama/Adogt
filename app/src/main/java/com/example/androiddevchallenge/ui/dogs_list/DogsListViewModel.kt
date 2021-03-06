/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.dogs_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.model.DogItem
import com.example.androiddevchallenge.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel
@Inject
constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val _dogs: MutableState<List<DogItem>> = mutableStateOf(ArrayList())
    val dogs: State<List<DogItem>>
        get() = _dogs

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean>
        get() = _loading

    init {
        retrieveDogItems()
    }

    private fun retrieveDogItems() {
        _loading.value = true
        repository.getAllDogItems().onEach {
            _dogs.value = it
            _loading.value = false
        }.launchIn(viewModelScope)
    }
}
