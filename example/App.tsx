import React from 'react';
import {Button, NativeModules, StyleSheet, Text, View} from 'react-native';
// @ts-ignore
import { Notify } from 'libnotify-rn';

function collectEmptyEvent() {
    Notify.collectEvent('ReactNativeRandomEvent_Empty', null, false, null);
}

export default function App() {
    return (
        <View style={styles.container}>
            <Text>Sample for ReactNative LibNotify. Click to any buttons:</Text>
            <Button
                onPress={collectEmptyEvent}
                title="Empty event"
                color="#FF6347"
            />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
});



