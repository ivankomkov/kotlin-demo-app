package util

import MyApplication
import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImageUtil{
    companion object {
        // save image with timestamp in name
        @Throws(IOException::class)
        fun createImageFile(imageBitmap: Bitmap): File {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_"
            val dir = MyApplication.appContext?.filesDir
            val image = File.createTempFile(imageFileName, ".jpg", dir)
            Log.d("createImageFile", image.absolutePath)
            try {
                FileOutputStream(image).use { out ->
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) // bmp is your Bitmap instance
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return image
        }
    }
}