package com.reactlibrary.libnotify;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.BuildConfig;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.mail.libnotify.api.NotificationFactory;
import ru.mail.notify.core.api.BackgroundAwakeMode;
import ru.mail.notify.core.api.NetworkSyncMode;

public class LibnotifyModule extends ReactContextBaseJavaModule {

    public LibnotifyModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
    }

    /**
     * Use in any JS class:
     * import {NativeModules} from 'react-native';
     * NativeModules.Notify.collectEvent('RandomEvent', 'Other', true);
     */
    @ReactMethod
    public void collectEvent(@NonNull String key,
                             @Nullable String value,
                             @NonNull Boolean deliverImmediately,
                             @Nullable ReadableMap props) {
        if (props == null) {
            if (TextUtils.isEmpty(value)) {
                NotificationFactory.get(getReactApplicationContext().getBaseContext())
                        .collectEvent(key, (boolean) deliverImmediately);
                return;
            }
            NotificationFactory.get(getReactApplicationContext().getBaseContext())
                    .collectEvent(key, value, deliverImmediately);
            return;
        }

        final Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, Object> entry : props.toHashMap().entrySet()) {
            params.put(entry.getKey(), String.valueOf(entry.getValue()));
        }

        if (TextUtils.isEmpty(value)) {
            NotificationFactory.get(getReactApplicationContext().getBaseContext())
                    .collectEvent(key, params, deliverImmediately);
            return;
        }

        NotificationFactory.get(getReactApplicationContext().getBaseContext())
                .collectEvent(key, value, params, deliverImmediately);
    }

    @ReactMethod
    public void collectEventWithNumber(@NonNull String key,
                                       @NonNull Integer value,
                                       @NonNull Boolean deliverImmediately,
                                       @Nullable ReadableMap props) {
        if (props == null) {
            NotificationFactory.get(getReactApplicationContext().getBaseContext())
                    .collectEvent(key, value, deliverImmediately);
            return;
        }

        final Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, Object> entry : props.toHashMap().entrySet()) {
            params.put(entry.getKey(), String.valueOf(entry.getValue()));
        }

        NotificationFactory.get(getReactApplicationContext().getBaseContext())
                .collectEvent(key, value, params, deliverImmediately);

    }

    @ReactMethod
    public void collectEventWithObject(@NonNull String key,
                                       @NonNull ReadableMap props,
                                       @NonNull Boolean deliverImmediately) {

        final Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, Object> entry : props.toHashMap().entrySet()) {
            params.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        NotificationFactory.get(getReactApplicationContext().getBaseContext())
                .collectEvent(key, props, params, deliverImmediately);
    }

    @ReactMethod
    public void setUserId(@Nullable String userId) {
        NotificationFactory.get(getReactApplicationContext().getBaseContext())
                .setUserId(userId, true);
    }

    @ReactMethod
    public void setProperty(@NonNull String key,
                            @Nullable String value) {
        if (TextUtils.isEmpty(value)) {
            NotificationFactory.get(getReactApplicationContext().getBaseContext())
                    .setProperty(key, value);
            return;
        }
        NotificationFactory.get(getReactApplicationContext().getBaseContext())
                .setProperty(key, value);
    }

    @ReactMethod
    public void setPropertyWithNumber(@NonNull String key,
                                      @NonNull Integer value) {
        NotificationFactory.get(getReactApplicationContext().getBaseContext())
                .setProperty(key, value);
    }

    @ReactMethod
    public void setPropertyWithObject(@NonNull String key,
                                      @NonNull ReadableMap value) {
        final Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, Object> entry : value.toHashMap().entrySet()) {
            params.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        NotificationFactory.get(getReactApplicationContext().getBaseContext())
                .setPropertyParams(key, params);
    }

    /**
     * @return module name for call from JS
     * <p>
     * Example:
     * For name "Notify"
     * NativeModules.Notify.setUserId("reactnative")
     * <p>
     * For name "BestLibrary"
     * NativeModules.BestLibrary.setUserId("reactnative")
     * <p>
     * e.t.c.
     */
    @NonNull
    @Override
    public String getName() {
        return "Notify";
    }

    @Override
    public void initialize() {
        super.initialize();
        //set optionally log receivers and enable debug mode with extended output
        if (BuildConfig.DEBUG) {
            NotificationFactory.enableDebugMode();
            NotificationFactory.setLogReceiver(new NotifyLogReceiver());
        }

        //an application could adjust network policy for libnotify using the following method
        NotificationFactory.setNetworkSyncMode(NetworkSyncMode.DEFAULT);

        //uncomment this to disable network communications
        //this method could be called from arbitrary application's logic
        //NotificationFactory.setNetworkSyncMode(NetworkSyncMode.DISABLED);

        //an application could enable/disable libnotify to use any alarms
        NotificationFactory.setBackgroundAwakeMode(BackgroundAwakeMode.DEFAULT);
        //NotificationFactory.setBackgroundAwakeMode(BackgroundAwakeMode.DISABLED);

        //initialize libnotify before any subsequent usage (this method is designed to be as
        //lightweight as possible, to avoid any applications's start performance impact)
        NotificationFactory.initialize(getReactApplicationContext().getBaseContext());

        //NOTE: if your application doesn't need to collect any events or call other libnotify methods,
        //your have to call the following method to start libnotify's internal processing logic
        //this method could also be called from some other place to make an application's launch
        //more lightweight (split object initialization and actual SDK thread start time)
        NotificationFactory.bootstrap(getReactApplicationContext().getBaseContext());

        //optionally track some custom events (also async method call)
        NotificationFactory.get(getReactApplicationContext().getBaseContext()).collectEvent("AppStarted");
    }
}
