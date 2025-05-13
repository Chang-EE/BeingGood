package com.example.beinggood.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.beinggood.exercise.ExerciseLogic
import com.example.beinggood.pose.PoseLandmarkerHelper
import com.example.beinggood.pose.imageProxyToBitmap
import com.google.mediapipe.framework.image.BitmapImageBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult

@Composable
fun PoseCameraScreen(
    exerciseName: String,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val counter: (PoseLandmarkerResult) -> Boolean = remember(exerciseName) {
        ExerciseLogic.getCounter(exerciseName)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "카메라 권한이 필요합니다", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    var count by remember { mutableStateOf(0) }
    var showCount by remember { mutableStateOf(false) }

    val poseHelper = remember {
        PoseLandmarkerHelper(
            context = context,
            onResults = { result ->
                if (counter(result)) {
                    count += 1
                    showCount = true
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(500)
                        showCount = false
                    }
                }
            }
        )
    }

    val previewView = remember { PreviewView(context) }

    DisposableEffect(Unit) {
        onDispose {
            poseHelper.close()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

        if (showCount) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.displayLarge,
                color = Color.Yellow,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(16.dp)
            )
        }

        LaunchedEffect(Unit) {
            val cameraProvider = ProcessCameraProvider.getInstance(context).get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val analyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                        val bitmap = imageProxyToBitmap(imageProxy)
                        val mpImage = BitmapImageBuilder(bitmap).build()
                        poseHelper.detectAsync(mpImage, System.currentTimeMillis() * 1000)
                        imageProxy.close()
                    }
                }

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_FRONT_CAMERA,
                preview,
                analyzer
            )
        }
    }
}


//package com.example.beinggood.ui.screen
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import com.example.beinggood.exercise.ExerciseLogic
//import com.example.beinggood.pose.PoseLandmarkerHelper
//import com.example.beinggood.pose.imageProxyToBitmap
//import com.google.mediapipe.framework.image.BitmapImageBuilder
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//@Composable
//fun PoseCameraScreen() {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    // 카메라 권한 요청
//    val permissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (!isGranted) {
//            Toast.makeText(context, "카메라 권한이 필요합니다", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            permissionLauncher.launch(Manifest.permission.CAMERA)
//        }
//    }
//
//    var count by remember { mutableStateOf(0) }
//    var showCount by remember { mutableStateOf(false) }
//
//    val poseHelper = remember {
//        PoseLandmarkerHelper(
//            context = context,
//            onResults = { result ->
//                if (ExerciseLogic.countSquat(result)) {
//                    count += 1
//                    showCount = true
//                    CoroutineScope(Dispatchers.Main).launch {
//                        delay(500)
//                        showCount = false
//                    }
//                }
//            }
//        )
//    }
//
//    val previewView = remember { PreviewView(context) }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            poseHelper.close()
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
//
//        if (showCount) {
//            Text(
//                text = "$count",
//                style = MaterialTheme.typography.displayLarge,
//                color = Color.Yellow,
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .background(Color.Black.copy(alpha = 0.5f))
//                    .padding(16.dp)
//            )
//        }
//
//        LaunchedEffect(Unit) {
//            val cameraProvider = ProcessCameraProvider.getInstance(context).get()
//            val preview = Preview.Builder().build().also {
//                it.setSurfaceProvider(previewView.surfaceProvider)
//            }
//
//            val analyzer = ImageAnalysis.Builder()
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build()
//                .also {
//                    it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
//                        val bitmap = imageProxyToBitmap(imageProxy)
//                        val mpImage = BitmapImageBuilder(bitmap).build()
//                        poseHelper.detectAsync(mpImage, System.currentTimeMillis() * 1000)
//                        imageProxy.close()
//                    }
//                }
//
//            cameraProvider.unbindAll()
//            cameraProvider.bindToLifecycle(
//                lifecycleOwner,
//                CameraSelector.DEFAULT_FRONT_CAMERA,
//                preview,
//                analyzer
//            )
//        }
//    }
//}
