# zmedia_saver

Save media file to library

A Flutter plugin for Android (iOS not implemented yet) for adding image path to Image library

## Installation

First, add ```zmedia_saver``` as [dependency in your pubspec.yaml](https://flutter.io/platform-plugins/) file.

### iOS

Not implemented

### Android

Add permission into your ```android/src/main/AndroidManifest.xml```:

```xml
...
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <application
...
```

## Example

```dart
...
import 'package:zmedia_saver/zmedia_saver.dart';

...
  Future<void> initPlatformState() async {
    // you can get image path using path_provider
    await ZmediaSaver.saveToLibrary('/storage/emulated/0/Pictures/CameraApp/1566620744553.png');
    ...
```