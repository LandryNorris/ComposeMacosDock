Compose Macos Dock
==================

This project is a native compose for macos app
that also allows the dock icon to be drawn via 
compose. This is only a proof of concept.

Building
--------

The app can be run from XCode.

The build can be finicky with cocoapods. If
XCode can't find symbols, run

```bash
./gradlew syncFramework -Pkotlin.native.cocoapods.platform=macosx -Pkotlin.native.cocoapods.archs=arm64 -Pkotlin.native.cocoapods.configuration=Debug
```

How it works
------------

```kotlin
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

val imageView = NSImageView()
imageView.image = NSImage(data = data)

NSApp?.dockTile?.contentView = imageView
NSApp?.dockTile?.display()
```

We use an ImageComposeScene to render the icon,
then convert it to png data. After this, we can
create an NSImage from the data and show it in a 
NSImageView. Finally, we pass the image view to 
NSApp's dockTile and tell it to display the new 
image.
