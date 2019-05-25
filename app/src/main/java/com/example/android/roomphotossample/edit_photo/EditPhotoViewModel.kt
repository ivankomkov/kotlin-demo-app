package com.example.android.roomphotossample.edit_photo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.roomphotossample.data.Photo

class EditPhotoViewModel(application: Application, photo: Photo) : AndroidViewModel(application) {
    var photo = MutableLiveData<Photo>()

    init {
        this.photo.value = photo
    }
}
