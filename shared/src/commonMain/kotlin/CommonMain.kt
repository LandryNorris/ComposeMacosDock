import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.delay
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import platform.AppKit.NSImage
import platform.AppKit.NSImageView
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes

var counter by mutableStateOf(0)

@Suppress("unused")
fun commonMain() {
    NSApplication.sharedApplication()

    Window {
        Column(modifier = Modifier.fillMaxSize().background(Color.LightGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Counter is $counter")
            Row {
                Button(onClick = { counter-- }) { Text("Decrement") }
                Button(onClick = { counter++ }) { Text("Increment") }
            }
        }

        LaunchedEffect(Unit) {
            while(true) {
                val imageView = NSImageView()
                imageView.image = createIcon()

                NSApp?.dockTile?.setContentView(imageView)
                NSApp?.dockTile?.display()
                delay(1000)
            }
        }
    }

    NSApp?.run()
}

fun createIcon(): NSImage {
    val scene = ImageComposeScene(100, 100) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White),
            contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Counter:")
                Text(counter.toString())
            }
        }
    }

    val bytes = scene.render().encodeToData()?.bytes ?: error("Unable to get Data from scene")

    val data = bytes.usePinned {
        NSData.dataWithBytes(it.addressOf(0), bytes.size.toULong())
    }

    return NSImage(data = data)
}
