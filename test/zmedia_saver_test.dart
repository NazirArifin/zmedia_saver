import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:zmedia_saver/zmedia_saver.dart';

void main() {
  const MethodChannel channel = MethodChannel('zmedia_saver');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await ZmediaSaver.platformVersion, '42');
  });
}
