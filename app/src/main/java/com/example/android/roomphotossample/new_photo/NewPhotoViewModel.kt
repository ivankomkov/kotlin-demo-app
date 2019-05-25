package com.example.android.roomphotossample.new_photo

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomphotossample.data.Photo
import com.example.android.roomphotossample.data.PhotoDatabase
import com.example.android.roomphotossample.data.PhotoRepository
import kotlinx.coroutines.launch
import util.ImageUtil

/**
 * View Model to keep a reference to the photo repository and
 * an up-to-date list of all photos.
 */

class NewPhotoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PhotoRepository
    // Using LiveData and caching what getAlphabetizedPhotos returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private var newPhoto = MutableLiveData<Photo>()

    init {
        val photosDao = PhotoDatabase.getDatabase(application, viewModelScope).photoDao()
        repository = PhotoRepository(photosDao)
        newPhoto.value = Photo(null)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun save() = viewModelScope.launch {
        repository.insert(newPhoto.value!!)
    }

    fun photo() = newPhoto.value

    fun setImage(image: Bitmap) {
        val img = ImageUtil.createImageFile(image)
        newPhoto.value?.path = img.absolutePath
    }
    fun setComment(comment: String) {
        newPhoto.value?.comment = comment
    }
    fun setDate(date: String) {
        newPhoto.value?.date = date
    }
    fun setLocation(location: String) {
        newPhoto.value?.location = location
    }
}
