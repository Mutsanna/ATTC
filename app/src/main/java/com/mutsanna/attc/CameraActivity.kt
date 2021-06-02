package com.mutsanna.attc

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.mutsanna.attc.networking.AppClient
import com.mutsanna.attc.networking.CreatePostResponse
import kotlinx.android.synthetic.main.activity_camera.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File
class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        btnTakePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            createPosts(photoFile)

            val fileProvider = FileProvider.getUriForFile(this, "com.mutsanna.attc.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(this, "Tidak bisa membuka kamera", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpload.setOnClickListener{
            createPosts(photoFile)
        }
    }

    private fun createPosts(photoFile: File) {
//        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        AppClient.instance.createPost(
                "${photoFile}"
        ).enqueue(object : Callback<CreatePostResponse> {
            override fun onResponse(call: Call<CreatePostResponse>, response: Response<CreatePostResponse>) {
                val responseText = "Response code: ${response.code()}\n" +
                        "prediction : ${response.body()?.prediction}\n" +
                        "success : ${response.body()?.success}\n"
                tvResnponse.text = responseText
            }

            override fun onFailure(call: Call<CreatePostResponse>, t: Throwable) {
                tvResnponse.text = t.message
            }

        })
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            imageView.setImageBitmap(takenImage)
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}