# zmedia_saver

Save media file to library

A Flutter plugin for Android (iOS not implemented yet) for adding image path to Image library

## Installation

First, add ```zmedia_saver``` as dependency in your pubspec.yaml file.

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