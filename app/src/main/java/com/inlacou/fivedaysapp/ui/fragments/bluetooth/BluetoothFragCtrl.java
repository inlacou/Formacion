package com.inlacou.fivedaysapp.ui.fragments.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inlacou.fivedaysapp.adapters.BluetoothDeviceRvAdapter;
import com.inlacou.fivedaysapp.bluetooth.BluetoothChatCtrl;
import com.inlacou.fivedaysapp.business.DeviceData;

import timber.log.Timber;

public class BluetoothFragCtrl {
	
	private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
	private BluetoothDeviceRvAdapter listAdapter;
	private BluetoothChatCtrl chatCtrl;
	
	private final BluetoothFrag view;
	private final BluetoothFragMdl model;
	
	public BluetoothFragCtrl(BluetoothFrag view, BluetoothFragMdl model) {
		this.view = view;
		this.model = model;
		listAdapter = new BluetoothDeviceRvAdapter(model.list, (view1, index, bluetoothDevice) -> {
			chatCtrl.connect(bluetoothDevice.bluetoothDevice, false);
		});
		
		// Initialize the BluetoothChatService to perform bluetooth connections
		chatCtrl = new BluetoothChatCtrl(view.getContext(), mHandler);
		chatCtrl.start();
	}
	
	public void populate() {
		view.rv.setAdapter(listAdapter);
		view.rv.setLayoutManager(new LinearLayoutManager(view.getActivity(), RecyclerView.VERTICAL, false));
		
		//TODO request location permissions to user, we already saw how.

		// Register for broadcasts when discovery has finished
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		view.getActivity().registerReceiver(mReceiver, filter);
	}
	
	public void update() {
		Timber.d("update");
		model.list.clear();
		btAdapter.startDiscovery();
	}
	
	private void onUpdated() {
		Timber.d("onUpdated");
		listAdapter.notifyDataSetChanged();
		view.onLoaded();
	}
	
	public void stop() {
		btAdapter.cancelDiscovery();
		onUpdated();
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Timber.d("ACTION " + action);
			
			if (BluetoothDevice.ACTION_FOUND==action) {
				// Discovery has found a device. Get the BluetoothDevice
				// object and its info from the Intent.
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String deviceName = device.getName();
				if (deviceName == null) deviceName = "Unknown";
				String deviceHardwareAddress = device.getAddress(); // MAC address
				if (deviceHardwareAddress == null) deviceHardwareAddress = "";

				DeviceData deviceData = new DeviceData(deviceName, deviceHardwareAddress, device);
				Timber.d("ACTION_FOUND | " + deviceData);
				if (!model.list.contains(deviceData)) {
					model.list.add(deviceData);
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED==action) {
				Timber.d("ACTION_DISCOVERY_FINISHED");
				onUpdated();
			}
		}
	};
	
	/**
	 * The Handler that gets information back from the BluetoothChatService
	 */
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case BluetoothChatCtrl.MESSAGE_STATE_CHANGE: {
					Timber.d("inkbluetooth | MESSAGE_STATE_CHANGE");
					switch(msg.arg1) {
						case BluetoothChatCtrl.STATE_CONNECTED: {
							Timber.d("inkbluetooth | MESSAGE_STATE_CHANGE | STATE_CONNECTED");
						}
						case BluetoothChatCtrl.STATE_CONNECTING: {
							Timber.d("inkbluetooth | MESSAGE_STATE_CHANGE | STATE_CONNECTING");
						}
						case BluetoothChatCtrl.STATE_LISTEN:
						case BluetoothChatCtrl.STATE_NONE: {
							Timber.d("inkbluetooth | MESSAGE_STATE_CHANGE | STATE_LISTEN or STATE_NONE");
						}
					}
					break;
				}
				case BluetoothChatCtrl.MESSAGE_WRITE: {
					Timber.d("inkbluetooth | MESSAGE_WRITE | ${String(msg.obj as ByteArray)}");
				}
				case BluetoothChatCtrl.MESSAGE_READ: {
					Timber.d("inkbluetooth | MESSAGE_READ | ${String(msg.obj as ByteArray, 0, msg.arg1)}");
					view.toast("Message received ${String(msg.obj as ByteArray, 0, msg.arg1)}");
				}
				case BluetoothChatCtrl.MESSAGE_DEVICE_NAME: {
					Timber.d("inkbluetooth | MESSAGE_DEVICE_NAME | " + msg.getData().getString(BluetoothChatCtrl.DEVICE_NAME));
					view.toast("Connected to ${msg.data.getString(Constants.DEVICE_NAME)}");
				}
				case BluetoothChatCtrl.MESSAGE_TOAST: {
					Timber.d("inkbluetooth | MESSAGE_TOAST");
					view.toast("Disconnected");
				}
			}
		}
	};
	
	public void onSendDummyMessageClick() {
		chatCtrl.write("dummy message".getBytes());
	}
}
