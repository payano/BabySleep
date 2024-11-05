package com.babysleep

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChildActivity : AppCompatActivity() {
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)

        findViewById<Button>(R.id.start_record).setOnClickListener {
            // Start and stop recording
        }

        initializeCamera()

    }

    private fun initializeCamera() {
        openCamera()

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        val cameraProvider = cameraProviderFuture.get()
        cameraExecutor = Executors.newSingleThreadExecutor()

        // Choosing the best quality.. might not be the best for this app...
        val qualitySelector = QualitySelector.fromOrderedList(
            listOf(Quality.UHD, Quality.FHD, Quality.HD, Quality.SD),
            FallbackStrategy.lowerQualityOrHigherThan(Quality.SD)
        )

        //enter the prioritized resolution and if none of them is available , fallback with the quality record
        //first step , liez video capture
        //-Creer un Recorder pour l'enregistrement de la video

        val recorder = Recorder.Builder()
            .setExecutor(cameraExecutor)
            .setQualitySelector(qualitySelector)
            .build()
        val videoCapture = VideoCapture.withOutput(recorder)

        try {
            // Bind use cases to camera
            cameraProvider.bindToLifecycle(
                this, CameraSelector.DEFAULT_BACK_CAMERA, videoCapture
            )
        } catch (exc: Exception) {
            Log.e("TAG", "Use case binding failed", exc)
        }
    }

    private fun openCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val preview = Preview.Builder().build()
            val imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                preview.setSurfaceProvider(findViewById<PreviewView>(R.id.viewFinder).surfaceProvider)

            } catch (exception: Exception) {
                // Handle camera setup errors
            }
        }, ContextCompat.getMainExecutor(this))
    }
}