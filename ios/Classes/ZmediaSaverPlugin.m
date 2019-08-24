#import "ZmediaSaverPlugin.h"
#import <zmedia_saver/zmedia_saver-Swift.h>

@implementation ZmediaSaverPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftZmediaSaverPlugin registerWithRegistrar:registrar];
}
@end
