package com.example.android.roomphotossample

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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.android.roomphotossample.data.Photo
import com.example.android.roomphotossample.data.PhotoDao
import com.example.android.roomphotossample.data.PhotoDatabase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class PhotoDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var photoDao: PhotoDao
    private lateinit var db: PhotoDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, PhotoDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        photoDao = db.photoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetWord() = runBlocking {
        val photo = Photo("word")
        photoDao.insert(photo)
        val allWords = photoDao.getPhotos().waitForValue()
        assertEquals(allWords[0].comment, photo.comment)
    }

    @Test
    @Throws(Exception::class)
    fun getAllWords() = runBlocking {
        val word = Photo("aaa")
        photoDao.insert(word)
        val word2 = Photo("bbb")
        photoDao.insert(word2)
        val allWords = photoDao.getPhotos().waitForValue()
        assertEquals(allWords[0].comment, word.comment)
        assertEquals(allWords[1].comment, word2.comment)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val word = Photo("word")
        photoDao.insert(word)
        val word2 = Photo("word2")
        photoDao.insert(word2)
        photoDao.deleteAll()
        val allWords = photoDao.getPhotos().waitForValue()
        assertTrue(allWords.isEmpty())
    }
}
