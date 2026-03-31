package com.example.cs712androidapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    private lateinit var btnCapture: Button
    private lateinit var btnBack: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        btnCapture = findViewById(R.id.btnCapture)
        btnBack = findViewById(R.id.btnBack)
        imageView = findViewById(R.id.imageView)

        // Open camera
        btnCapture.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 100)
        }

        // Go back to MainActivity
        btnBack.setOnClickListener {
            finish()
        }
    }

    // Handle captured image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
    }
}