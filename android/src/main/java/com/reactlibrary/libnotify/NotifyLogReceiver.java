
package com.reactlibrary.libnotify;

import android.util.Log;

import ru.mail.notify.core.utils.LogReceiver;

/**
 * Helper class for logging.
 * Please, disable log in production (use if(BuildConfig.DEBUG) { setLogReceiver(new NotifyLogReceiver()) })
 */
public class NotifyLogReceiver implements LogReceiver {
    @Override
    public void v(String tag, String message) {
        Log.d("LIBNOTIFY-" + tag, message);
    }

    @Override
    public void v(String tag, String message, Throwable exception) {
        Log.v("LIBNOTIFY-" + tag, message, exception);
    }

    @Override
    public void e(String tag, String message) {
        Log.e("LIBNOTIFY-" + tag, message);
    }

    @Override
    public void e(String tag, String message, Throwable exception) {
        Log.e("LIBNOTIFY-" + tag, message, exception);
    }

    @Override
    public void d(String tag, String message) {
        Log.d("LIBNOTIFY-" + tag, message);
    }

    @Override
    public void d(String tag, String message, Throwable exception) {
        Log.d("LIBNOTIFY-" + tag, message, exception);
    }

}
