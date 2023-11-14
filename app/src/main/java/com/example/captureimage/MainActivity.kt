package com.example.captureimage

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        button.isEnabled=false

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),111)
        }
        else{
            button.isEnabled=true

            button.setOnClickListener(){
                var i=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(i,101)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                val pic = data?.getParcelableExtra<Bitmap>("data")
                Log.d("CaptureImage", "Image captured successfully.")
                if (pic != null) {
                    Log.d("CaptureImage", "Displaying captured image.")
                    val imageView = findViewById<ImageView>(R.id.imageView)
                    imageView.setImageBitmap(pic)
                } else {
                    Log.d("CaptureImage", "Image data is null.")
                }
            } else {
                Log.d("CaptureImage", "Image capture failed. ResultCode: $resultCode")
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            button.isEnabled=true
        }
    }
}