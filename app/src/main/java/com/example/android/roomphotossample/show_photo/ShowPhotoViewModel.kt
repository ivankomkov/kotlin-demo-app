package com.example.android.roomphotossample.show_photo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomphotossample.data.Photo
import com.example.android.roomphotossample.data.PhotoDatabase
import com.example.android.roomphotossample.data.PhotoRepository
import kotlinx.coroutines.launch

class ShowPhotoViewModel(application: Application) : AndroidViewModel(application) {
    var photo = MutableLiveData<Photo>()
    private val repository: PhotoRepository

    init {
        val photosDao = PhotoDatabase.getDatabase(application, viewModelScope).photoDao()
        repository = PhotoRepository.getRepository(photosDao)
    }

    fun nextPhoto() = viewModelScope.launch {
        photo.value = repository.nextPhoto(photo.value)
    }

    fun previousPhoto() = viewModelScope.launch {
        photo.value = repository.previousPhoto(photo.value)
    }
}
