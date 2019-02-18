/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, NativeModules, DeviceEventEmitter, ToastAndroid } from 'react-native';

type Props = {};

import {
  AppRegistry
} from 'react-native';
const MyTaskService = async (data) => {
  console.log('bacl:', data);
  ToastAndroid.show(`AudioBridgeEvent: ${data.hasInternet}`, ToastAndroid.SHORT);
}
AppRegistry.registerHeadlessTask('MyTaskService', () => MyTaskService);

export default class DemoView extends Component {

  componentDidMount() {
    DeviceEventEmitter.addListener('AudioBridgeEvent', function (e: Event) {
      console.log('keyboardWillShow handler:', e);
      ToastAndroid.show(`AudioBridgeEvent: ${e}`, ToastAndroid.SHORT);
    });
  }

  bgServiceAction() {
    NativeModules.BackgroundService.startBackgroundService(data => {
      console.log('startBackgroundService:', data);
    });
  }
  bgServiceCancelAction() {
    NativeModules.BackgroundService.cancelBackgroundService(data => {
      console.log('cancelBackgroundService:', data);
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} onPress={this.bgServiceAction.bind(this)}>Start Background Service</Text>
        <Text style={styles.welcome} onPress={this.bgServiceCancelAction.bind(this)}>Cancel Service</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 30,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
