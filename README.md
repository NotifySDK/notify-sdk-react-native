# react-native-libnotify

## Install

1) Add dependency with `$ npm install libnotify-rn --save`
2) Set up Android side

### Android

1) Create if not exist `GcmMessageHandlerService.java`. Example:

```java
/**
 * Resend GCM message from your application to Notify
 */
public class GcmMessageHandlerService extends FirebaseMessagingService {
    private static final String LOG_TAG = "GcmMessageHandlerService";

    @Override
    public void onMessageReceived(RemoteMessage message) {
        String from = message.getFrom();
        Map<String, String> data = message.getData();
        FileLog.v(LOG_TAG, "message received from %s with data %s", from, data);
        if (from == null) {
            return;
        }
        NotificationFactory.deliverGcmMessageIntent(this, from, data);
    }

    @Override
    public void onNewToken(String token) {
        FileLog.v(LOG_TAG, "token refresh. onNewToken: %s", token);
        NotificationFactory.refreshGcmToken(this);
    }
}
```

2) Add your handler in `AndroidManifest.xml`

```xml
<service
    android:name=".GcmMessageHandlerService"
    android:exported="false">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
</service>
```

3) Set up credentials and other values in xml (see [example/android/app/src/main/res/values/libnotify.xml](https://github.com/NotifySDK/notify-sdk-react-native/tree/master/android/app/src/main/res/values/libnotify.xml))

```xml
<string name="libnotify_application_name">android-libnotify-app</string>
<string name="libnotify_application_id">3</string>
<string name="libnotify_application_secret">21d848ed4e77710c6ce1b185bafbe4cb0f8ed5fc6aa51cf303c27b8099a8d104</string>
<string name="libnotify_server_id">297109036349</string>

and other...
```

### iOS

TODO

## Usage
```javascript
import { Notify } from 'libnotify-rn';

// Collect event
Notify.collectEvent('ReactNativeRandomEvent_Empty');
// Collect event with value
Notify.collectEvent('ReactNativeRandomEvent', 1);
Notify.collectEvent('ReactNativeRandomEvent', 'Some value');
// Collect event deliverImmediately
Notify.collectEvent('ReactNativeRandomEvent', 'Some value', true);
// Collect event with property
Notify.collectEvent('ReactNativeRandomEvent', 'Some value', true, {'customprop': 'key'});


// Set property
Notify.setProperty('EmptyProp');
// Set property with value
Notify.setProperty('MyProp', 1);
Notify.setProperty('MyProp', 'test');
Notify.setProperty('MyProp', {'customprop': 'key'});
```
