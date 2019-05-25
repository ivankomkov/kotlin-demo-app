package com.example.android.roomphotossample.edit_photo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.roomphotossample.R

class EditActivity : AppCompatActivity() {

    lateinit var mEditPhotoViewModel: EditPhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_edit)
    }

    companion object {
        val TAG: String = "EditActivity"
    }
}