package com.example.android.roomphotossample.data

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

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
interface PhotoDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from photo")
    fun getPhotos(): LiveData<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photo: Photo): Long

    @Query("DELETE FROM photo")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(entity: Photo)

//    fun upsert(entity: Photo) {
//        val id = insert(entity)
//        if (id == -1L) {
//            update(entity)
//        }
//    }
}
