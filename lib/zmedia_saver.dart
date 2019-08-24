import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:path/path.dart';
import 'package:permission_handler/permission_handler.dart';

class ZmediaSaver {
  static const MethodChannel _channel =
      const MethodChannel('zmedia_saver');

  static Future<bool> saveToLibrary(String filePath) async {
    final File theFile = File(filePath);
    final Directory theDir = theFile.parent;
    final Map<String, String> params = <String, String> {
      "directory": theDir.path,
      "file": basename(filePath),
      "ext": extension(filePath).toLowerCase()
    };
    if (await PermissionHandler().checkPermissionStatus(PermissionGroup.storage) == PermissionStatus.denied) {
      await PermissionHandler().requestPermissions([PermissionGroup.storage]);
    }
    final bool result = await _channel.invokeMethod('saveToLibrary', params);
    return result;
  }

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
