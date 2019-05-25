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

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import com.example.android.roomphotossample.new_photo.NewPhotoActivity
import com.example.android.roomphotossample.R


class MainActivity : AppCompatActivity() {

    private val newPhotoActivityRequestCode = 1
    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PhotoListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedPhotos.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        photoViewModel.allPhotos.observe(this, Observer { photos ->
            // Update the cached copy of the photos in the adapter.
            photos?.let { adapter.setPhotos(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPhotoActivity::class.java)
            startActivityForResult(intent, newPhotoActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

//        if (requestCode == newPhotoActivityRequestCode && resultCode == Activity.RESULT_OK) {
//            intentData?.let { data ->
//                val photo = Photo(data.getStringExtra(NewPhotoActivity.EXTRA_REPLY))
//                photoViewModel.insert(photo)
//            }
//        } else {
//            Toast.makeText(
//                    applicationContext,
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG
//            ).show()
//        }
    }
}
