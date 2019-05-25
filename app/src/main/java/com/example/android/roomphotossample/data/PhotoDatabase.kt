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

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Photo::class], version = 7, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var INSTANCE: PhotoDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): PhotoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PhotoDatabase::class.java,
                        "photo"
                )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .addCallback(PhotoDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class PhotoDatabaseCallback(
                private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
//                INSTANCE?.let { database ->
//                    scope.launch {
//                        populateDatabase(database.photoDao())
//                    }
//                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more photos, just add them.
         */
//        suspend fun populateDatabase(photoDao: PhotoDao) {
//            // Example of callback
//        }
    }

}
