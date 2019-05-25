package com.example.android.roomphotossample.main

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomphotossample.data.Photo
import com.example.android.roomphotossample.data.PhotoDatabase
import com.example.android.roomphotossample.data.PhotoRepository
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the photo repository and
 * an up-to-date list of all photos.
 */

class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PhotoRepository
    // Using LiveData and caching what getAlphabetizedPhotos returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.`
    // - Repository is completely separated from the UI through the ViewModel.
    val allPhotos: LiveData<List<Photo>>

    init {
        val photosDao = PhotoDatabase.getDatabase(application, viewModelScope).photoDao()
        repository = PhotoRepository(photosDao)
        allPhotos = repository.allPhotos
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(photo: Photo) = viewModelScope.launch {
        repository.insert(photo)
    }
}
