package com.mutsanna.attc.camera

//import android.graphics.BitmapFactory
//import android.os.Environment
//import androidx.core.content.FileProvider

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.mutsanna.attc.databinding.ActivityCameraBinding
import com.mutsanna.attc.networking.AppClient
import com.mutsanna.attc.networking.CreatePostResponse
import kotlinx.android.synthetic.main.activity_camera.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*


class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            cameraCheckPermission()
        }

        binding.btnGallery.setOnClickListener {
            galleryCheckPermission()
        }

        binding.btnUpload.setOnClickListener {
            createPosts()
        }

        //when you click on the image
        binding.imageView.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle("Select Action")
            val pictureDialogItem = arrayOf(
                "Select photo from Gallery",
                "Capture photo from Camera"
            )
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->

                when (which) {
                    0 -> gallery()
                    1 -> camera()
                }
            }

            pictureDialog.show()
        }

    }

    private fun galleryCheckPermission() {

        Dexter.withContext(this).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    this@CameraActivity,
                    "You have denied the storage permission to select image",
                    Toast.LENGTH_SHORT
                ).show()
                showRotationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?, p1: PermissionToken?
            ) {
                showRotationalDialogForPermission()
            }
        }).onSameThread().check()
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }


    private fun cameraCheckPermission() {

        Dexter.withContext(this)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }


    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    val bitmap = data?.extras?.get("data") as Bitmap

                    val bytes = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    val image = bytes.toByteArray()
                    val mampus = image.toString()

                    val requestBody = RequestBody.create(MediaType.parse("multipart/form"), image)

                    val palu = MultipartBody.Part.createFormData("image", mampus, requestBody)

                    //we are using coroutine image loader (coil)
                    binding.imageView.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }

                    AppClient.instance.createPost(palu).enqueue(object :
                        Callback<CreatePostResponse> {
                        override fun onResponse(
                            call: Call<CreatePostResponse>,
                            response: Response<CreatePostResponse>
                        ) {
                            val responseText = "Response code: ${response.code()}\n" +
                                    "filename : ${response.body()?.filename}\n" +
                                    "input : ${response.body()?.prediction}\n" +
                                    "success : ${response.body()?.success}\n"
                            tvResnponse.text = responseText
                        }

                        override fun onFailure(call: Call<CreatePostResponse>, t: Throwable) {
                            tvResnponse.text = t.message
                        }

                    })
                }

                GALLERY_REQUEST_CODE -> {

                    binding.imageView.load(data?.data)
//                    val image = data?.data
//                    AppClient.instance.createPost(image).enqueue(object : Callback<CreatePostResponse> {
//                        override fun onResponse(call: Call<CreatePostResponse>, response: Response<CreatePostResponse>) {
//                            val responseText = "Response code: ${response.code()}\n" +
//                                    "filename : ${response.body()?.filename}\n" +
//                                    "input : ${response.body()?.prediction}\n" +
//                                    "success : ${response.body()?.success}\n"
//                            tvResnponse.text = responseText
//                        }
//
//                        override fun onFailure(call: Call<CreatePostResponse>, t: Throwable) {
//                            tvResnponse.text = t.message
//                        }
//
//                    })

                }
            }

        }

    }


    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage(
                "It looks like you have turned off permissions"
                        + "required for this feature. It can be enable under App settings!!!"
            )

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun createPosts() {
//        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        AppClient.instance.createPost().enqueue(object : Callback<CreatePostResponse> {
//            override fun onResponse(call: Call<CreatePostResponse>, response: Response<CreatePostResponse>) {
//                val responseText = "Response code: ${response.code()}\n" +
//                        "input : ${response.body()?.prediction}\n" +
//                        "success : ${response.body()?.success}\n"
//                tvResnponse.text = responseText
//            }
//
//            override fun onFailure(call: Call<CreatePostResponse>, t: Throwable) {
//                tvResnponse.text = t.message
//            }
//
//        })
    }

//    private fun getPhotoFile(fileName: String): File {
//        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(fileName, ".jpg", storageDirectory)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//
//            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
//
//            imageView.setImageBitmap(takenImage)
//        }else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }
}