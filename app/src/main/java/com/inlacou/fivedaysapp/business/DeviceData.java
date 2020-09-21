package com.inlacou.fivedaysapp.business;

import android.bluetooth.BluetoothDevice;

import java.util.Objects;

public class DeviceData {

	public String deviceName;
	public String deviceHardwareAddress;
	public BluetoothDevice bluetoothDevice;
	
	public DeviceData(String deviceName, String deviceHardwareAddress, BluetoothDevice bluetoothDevice) {
		this.deviceName = deviceName;
		this.deviceHardwareAddress = deviceHardwareAddress;
		this.bluetoothDevice = bluetoothDevice;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DeviceData)) return false;
		DeviceData that = (DeviceData) o;
		return Objects.equals(deviceName, that.deviceName) &&
				Objects.equals(deviceHardwareAddress, that.deviceHardwareAddress);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(deviceName, deviceHardwareAddress);
	}
	
	@Override
	public String toString() {
		return "DeviceData{" +
				"deviceName='" + deviceName + '\'' +
				", deviceHardwareAddress='" + deviceHardwareAddress + '\'' +
				'}';
	}
}
