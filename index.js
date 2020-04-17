import {
    NativeEventEmitter,
    NativeModules,
    Platform,
} from 'react-native';

const module_notify = NativeModules.Notify;

var Notify = {};

Notify.collectEvent = function (key, value = null, deliverImmediately = false, props = null) {
    if (value === null && props !== null) {
        module_notify.collectEventWithObject(key, deliverImmediately, props)
        return
    }
    if (Number.isInteger(value)) {
        module_notify.collectEventWithNumber(key, value, deliverImmediately, props)
        return;
    }
    module_notify.collectEvent(key, value, deliverImmediately, props)
}

Notify.setUserId = module_notify.setUserId

Notify.setProperty = function (key, value = null) {
    const value_type = typeof value
    if (value === null) {
        module_notify.setProperty(key, value)
        return;
    }
    if (value_type === "object") {
        module_notify.setPropertyWithObject(key, value)
        return;
    }
    if (Number.isInteger(value)) {
        module_notify.setPropertyWithNumber(key, value)
        return;
    }
    module_notify.setProperty(key, value)
}
