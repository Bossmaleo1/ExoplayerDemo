package com.exoplayertemplate.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

//const val EXAMPLE_VIDEO_URI = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

const val EXAMPLE_VIDEO_URI = "https://blog.codabee.com/wp-content/uploads/2023/12/170272-540p.mp4"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //VideoPlayerScreen("https://blog.codabee.com/wp-content/uploads/2023/12/170272-540p.mp4")
            //VideoPlayerScreen(videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
            ExoPlayerView()
        }
    }
}



/**
* Composable function that displays an ExoPlayer to play a video using Jetpack Compose.
*
* @OptIn annotation to UnstableApi is used to indicate that the API is still experimental and may
* undergo changes in the future.
*
* @see EXAMPLE_VIDEO_URI Replace with the actual URI of the video to be played.
*/
@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerView() {
    // Get the current context
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context).build()

    // Create a MediaSource
    val mediaSource = remember(EXAMPLE_VIDEO_URI) {
        MediaItem.fromUri(EXAMPLE_VIDEO_URI)
    }

    // Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Use AndroidView to embed an Android View (PlayerView) into Compose
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight() // Set your desired height
        )
    }

}


