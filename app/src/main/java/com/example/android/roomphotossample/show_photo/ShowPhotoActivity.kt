package com.example.android.roomphotossample.show_photo

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.roomphotossample.R
import com.example.android.roomphotossample.data.Photo
import com.example.android.roomphotossample.ui.OnSwipeTouchListener
import org.w3c.dom.Text

class ShowPhotoActivity : AppCompatActivity() {
    lateinit var mShowPhotoViewModel: ShowPhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_photo)

        mShowPhotoViewModel  = ViewModelProviders.of(this).get(ShowPhotoViewModel::class.java)
//        mShowPhotoViewModel.photo =

        mShowPhotoViewModel.photo?.value = intent.getSerializableExtra("photo") as Photo

        mShowPhotoViewModel.photo.observe (this, Observer{ photo ->

            findViewById<ImageView>(R.id.show_imageView).setImageBitmap(BitmapFactory.decodeFile(photo.path))
            findViewById<TextView>(R.id.show_riComment).text = photo.comment
            findViewById<TextView>(R.id.show_riDate).text = photo.date
            findViewById<TextView>(R.id.show_riLocation).text = photo.location
        })
//
        val btnCancel = findViewById<Button>(R.id.show_btnBack)
        btnCancel.setOnClickListener {
            val replyIntent = Intent()
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish()
        }

        findViewById<ImageView>(R.id.show_imageView).setOnTouchListener(OnSwipeTouchListener(
                right = {
                    mShowPhotoViewModel.previousPhoto()
                },
                left = {
                    mShowPhotoViewModel.nextPhoto()
                }))
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                true
            }
            MotionEvent.ACTION_MOVE -> {
                true
            }
            MotionEvent.ACTION_UP -> {
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    companion object {
        val TAG: String = "ShowPhotoActivity"
    }
}