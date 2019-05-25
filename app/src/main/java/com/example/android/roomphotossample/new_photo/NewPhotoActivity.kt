package com.example.android.roomphotossample.new_photo

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

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.android.roomphotossample.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

/**
 * Activity for entering a photo.
 */

class NewPhotoActivity : AppCompatActivity() {

    private lateinit var mCommentView: EditText
    private lateinit var mNewPhotoViewModel: NewPhotoViewModel
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_LOCATION_FINE = 2
    private val REQUEST_LOCATION_COARSE = 3
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_photo)
        mCommentView = findViewById(R.id.riComment)
        mNewPhotoViewModel = ViewModelProviders.of(this).get(NewPhotoViewModel::class.java)

        val btnCancel = findViewById<Button>(R.id.button_cancel)
        btnCancel.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish()
        }

        val btnSnap = findViewById<Button>(R.id.button_snap)
        btnSnap.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

        val btnSave = findViewById<Button>(R.id.button_save)
        btnSave.setOnClickListener {
            val replyIntent = Intent()
            // no photo
            if (mNewPhotoViewModel.photo()?.path == null) {
                Toast.makeText(
                        applicationContext,
                        "Photo not saved because no photo taken",
                        Toast.LENGTH_LONG
                ).show()
            } else {
                val txtComment = findViewById<TextView>(R.id.riComment)
                mNewPhotoViewModel.setComment(txtComment.text.toString())
                mNewPhotoViewModel.save()
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }

        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_LOCATION_COARSE)

        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_FINE)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        // Got last known location. In some rare situations this can be null.
                        mNewPhotoViewModel.setLocation(location.toString())
                    }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageView = findViewById<ImageView>(R.id.imageView)
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            mNewPhotoViewModel.setImage(imageBitmap)
            mNewPhotoViewModel.setDate(Date().toString())
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.photolistsql.REPLY"
    }
}

