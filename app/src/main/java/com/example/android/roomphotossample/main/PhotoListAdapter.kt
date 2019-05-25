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

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.android.roomphotossample.R
import com.example.android.roomphotossample.data.Photo
import com.example.android.roomphotossample.edit_photo.EditActivity


class PhotoListAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var photos = emptyList<Photo>() // Cached copy of photos
    private lateinit var mListener: OnItemClickListener

    inner class PhotoViewHolder(itemView: View, onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.riImage)
        val commentView: TextView = itemView.findViewById(R.id.riComment)
        val dateView: TextView = itemView.findViewById(R.id.riDate)
        val locationView: TextView = itemView.findViewById(R.id.riLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return PhotoViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val current = photos[position]
        holder.commentView.text = current.comment
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(current.path))
        holder.dateView.text = current.date
        holder.locationView.text = current.location

        holder.itemView.setOnClickListener { view ->
            val photo = photos.get(position)
            var intent = Intent(view.context, EditActivity::class.java)
            intent.putExtra("photoId", photo.id)
            view.context.startActivity(intent)
        }
    }

    internal fun setPhotos(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    override fun getItemCount() = photos.size
}


