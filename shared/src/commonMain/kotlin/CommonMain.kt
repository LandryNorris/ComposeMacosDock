import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.AppKit.NSApp
import platform.AppKit.NSApplication
import platform.AppKit.NSImage
import platform.AppKit.NSImageView
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes

fun commonMain() {
    NSApplication.sharedApplication()

    Window {
        LaunchedEffect(Unit) {
            while(true) {
                val imageView = NSImageView()
                imageView.image = createIcon()

                NSApp?.dockTile?.setContentView(imageView)
                NSApp?.dockTile?.display()
            }
        }
    }

    NSApp?.run()
}

fun createIcon(): NSImage {
    val scene = ImageComposeScene(128, 128) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Text("Test")
        }
    }

    val bytes = scene.render().encodeToData()?.bytes ?: error("Unable to get Data from scene")

    val data = bytes.usePinned {
        NSData.dataWithBytes(it.addressOf(0), bytes.size.toULong())
    }

    return NSImage(data = data)
}
