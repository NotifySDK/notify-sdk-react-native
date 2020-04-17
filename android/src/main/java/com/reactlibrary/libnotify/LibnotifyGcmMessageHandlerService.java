package com.reactlibrary.libnotify;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ru.mail.libnotify.api.NotificationFactory;
import ru.mail.notify.core.utils.FileLog;

public class LibnotifyGcmMessageHandlerService extends FirebaseMessagingService {
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
        super.onMessageReceived(message);
    }

    @Override
    public void onNewToken(String token) {
        FileLog.v(LOG_TAG, "token refresh. onNewToken: %s", token);
        NotificationFactory.refreshGcmToken(this);
        super.onNewToken(token);
    }
}
