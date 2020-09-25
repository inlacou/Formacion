package com.inlacou.fivedaysapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import timber.log.Timber;

public class BluetoothChatCtrl {
	
	// Member fields
	private BluetoothAdapter mAdapter;
	private Handler mHandler;
	private AcceptThread mSecureAcceptThread = null;
	private AcceptThread mInsecureAcceptThread = null;
	private ConnectThread mConnectThread = null;
	private ConnectedThread mConnectedThread = null;
	private Integer mState = 0;

	// Unique UUID for this application
	private UUID MY_UUID_SECURE = UUID.fromString("0000110a-0000-1000-8000-00805f9b34fb");
	private UUID MY_UUID_INSECURE = UUID.fromString("d620cd2b-e0a4-435b-b02e-40324d57195b");
	
	// Name for the SDP record when creating server socket
	private String NAME_SECURE = "BluetoothChatSecure";
	private String NAME_INSECURE = "BluetoothChatInsecure";
	
	// Constants that indicate the current connection state
	public static final int STATE_NONE = 0;        // we're doing nothing
	public static final int STATE_LISTEN = 1;      // now listening for incoming connections
	public static final int STATE_CONNECTING = 2;  // now initiating an outgoing connection
	public static final int STATE_CONNECTED = 3;   // now connected to a remote device
	
	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final int MESSAGE_TYPE_SENT = 0;
	public static final int MESSAGE_TYPE_RECEIVED = 1;
	
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	
	public BluetoothChatCtrl(Context context, Handler handler) {
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mState = STATE_NONE;
		mHandler = handler;
	}
	
	/**
	 * Return the current connection state.
	 */
	public synchronized int getState() {
		return mState;
	}
	
	/**
	 * Start the chat service. Specifically start AcceptThread to begin a
	 * session in listening (server) mode. Called by the Activity onResume()
	 */
	public synchronized void start() {
		Timber.d("start");
		
		// Cancel any thread attempting to make a connection
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		
		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		
		// Start the thread to listen on a BluetoothServerSocket
		if (mSecureAcceptThread == null) {
			mSecureAcceptThread = new AcceptThread(true);
			mSecureAcceptThread.start();
		}
		if (mInsecureAcceptThread == null) {
			mInsecureAcceptThread = new AcceptThread(false);
			mInsecureAcceptThread.start();
		}
		// Update UI title
		//updateUserInterfaceTitle()
	}
	
	/**
	 * Start the ConnectThread to initiate a connection to a remote device.
	 * @param device The BluetoothDevice to connect
	 * @param secure Socket Security type - Secure (true) , Insecure (false)
	 */
	public synchronized void connect(BluetoothDevice device, Boolean secure) {
		Timber.d("connect to: " + device);
		
		// Cancel any thread attempting to make a connection
		if (mState == STATE_CONNECTING) {
			if (mConnectThread != null) {
				mConnectThread.cancel();
				mConnectThread = null;
			}
		}
		
		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		
		// Start the thread to connect with the given device
		mConnectThread = new ConnectThread(device, secure);
		mConnectThread.start();
		
		// Update UI title
		//updateUserInterfaceTitle()
	}
	
	/**
	 * Start the ConnectedThread to begin managing a Bluetooth connection
	 * @param socket The BluetoothSocket on which the connection was made
	 * @param device The BluetoothDevice that has been connected
	 */
	public synchronized void connected(BluetoothSocket socket, BluetoothDevice device, String socketType) {
		Timber.d("connected, Socket Type: " + socketType);
		
		// Cancel the thread that completed the connection
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		
		// Cancel any thread currently running a connection
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		
		// Cancel the accept thread because we only want to connect to one device
		if (mSecureAcceptThread != null) {
			mSecureAcceptThread.cancel();
			mSecureAcceptThread = null;
		}
		if (mInsecureAcceptThread != null) {
			mInsecureAcceptThread.cancel();
			mInsecureAcceptThread = null;
		}
		
		// Start the thread to manage the connection and perform transmissions
		mConnectedThread = new ConnectedThread(socket, socketType);
		mConnectedThread.start();
		
		// Send the name of the connected device back to the UI Activity
		Message msg = mHandler.obtainMessage(MESSAGE_DEVICE_NAME);
		Bundle bundle = new Bundle();
		bundle.putString(DEVICE_NAME, device.getName());
		msg.setData(bundle);
		mHandler.sendMessage(msg);
		// Update UI title
		//updateUserInterfaceTitle()
	}
	
	/**
	 * Stop all threads
	 */
	public synchronized void stop() {
		Timber.d("stop");
		
		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}
		
		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
		
		if (mSecureAcceptThread != null) {
			mSecureAcceptThread.cancel();
			mSecureAcceptThread = null;
		}
		
		if (mInsecureAcceptThread != null) {
			mInsecureAcceptThread.cancel();
			mInsecureAcceptThread = null;
		}
		
		mState = STATE_NONE;
		// Update UI title
		//updateUserInterfaceTitle()
	}
	
	/**
	 * Write to the ConnectedThread in an unsynchronized manner
	 * @param out The bytes to write
	 */
	public void write(byte[] out) {
		// Create temporary object
		ConnectedThread r = null;
		// Synchronize a copy of the ConnectedThread
		synchronized(this) {
			if (mState != STATE_CONNECTED) return;
			else r = mConnectedThread;
		}
		// Perform the write unsynchronized
		r.write(out);
	}
	
	/**
	 * Indicate that the connection attempt failed and notify the UI Activity.
	 */
	private void connectionFailed() {
		// Send a failure message back to the Activity
		Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
		Bundle bundle = new Bundle();
		bundle.putString(TOAST, "Unable to connect device");
		msg.setData(bundle);
		mHandler.sendMessage(msg);
		
		mState = STATE_NONE;
		// Update UI title
		//updateUserInterfaceTitle()
		
		// Start the service over to restart listening mode
		this.start();
	}
	
	/**
	 * Indicate that the connection was lost and notify the UI Activity.
	 */
	private void connectionLost() {
		// Send a failure message back to the Activity
		Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
		Bundle bundle = new Bundle();
		bundle.putString(TOAST, "Device connection was lost");
		msg.setData(bundle);
		mHandler.sendMessage(msg);
		
		mState = STATE_NONE;
		// Update UI title
		// updateUserInterfaceTitle()
		
		// Start the service over to restart listening mode
		this.start();
	}
	
	/**
	 * This thread runs while listening for incoming connections. It behaves
	 * like a server-side client. It runs until a connection is accepted
	 * (or until cancelled).
	 */
	private class AcceptThread extends Thread {
		
		// The local server socket
		private BluetoothServerSocket mmServerSocket;
		private String mSocketType;
		
		public AcceptThread(Boolean secure) {
			super();
			
			BluetoothServerSocket tmp = null;
			mSocketType = (secure) ? "Secure" : "Insecure";
			
			// Create a new listening server socket
			try {
				if (secure) {
					tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);
				} else {
					tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);
				}
			} catch (IOException e) {
				Timber.e("Socket Type: " + mSocketType + " listen() failed " + e);
			}
			
			mmServerSocket = tmp;
			mState = STATE_LISTEN;
		}
		
		@Override
		public void run() {
			Timber.d("Socket Type: " + mSocketType + " BEGIN mAcceptThread " + this);
			String name = "AcceptThread " + mSocketType;
			
			BluetoothSocket socket;
			
			// Listen to the server socket if we're not connected
			while (mState != STATE_CONNECTED) {
				try {
					// This is a blocking call and will only return on a
					// successful connection or an exception
					socket = mmServerSocket.accept();
				} catch (IOException e) {
					Timber.e("Socket Type: " + mSocketType + " accept() failed " + e);
					break;
				}
				
				// If a connection was accepted
				if (socket != null) {
					synchronized(this) {
						switch(mState) {
							case STATE_LISTEN:
							case STATE_CONNECTING: {
								// Situation normal. Start the connected thread.
								connected(socket, socket.getRemoteDevice(), mSocketType);
								break;
							}
							case STATE_NONE:
							case STATE_CONNECTED: {
								// Either not ready or already connected. Terminate new socket.
								try {
									socket.close();
								} catch (IOException e) {
									Timber.e("Could not close unwanted socket " + e);
								}
								break;
							}
							default: break;
						}
					}
				}
			}
			Timber.i("END mAcceptThread, socket Type: " + mSocketType);
		}
		
		public void cancel() {
			Timber.d("Socket Type " + mSocketType + " cancel " + this);
			try {
				mmServerSocket.close();
			} catch (IOException e) {
				Timber.e("Socket Type " + mSocketType + " close() of server failed " + e);
			}
		}
	}
	
	/**
	 * This thread runs while attempting to make an outgoing connection
	 * with a device. It runs straight through; the connection either
	 * succeeds or fails.
	 */
	private class ConnectThread extends Thread {
		private Boolean secure;
		private BluetoothDevice mmDevice;
		private BluetoothSocket mmSocket;
		private String mSocketType;
		
		public ConnectThread(BluetoothDevice mmDevice, Boolean secure) {
			super();
			this.mmDevice = mmDevice;
			this.secure = secure;
			BluetoothSocket tmp = null;
			mSocketType = (secure) ? "Secure" : "Insecure";
			
			// Get a BluetoothSocket for a connection with the
			// given BluetoothDevice
			try {
				if (secure) {
					tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
				} else {
					tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
				}
			} catch (IOException e) {
				Timber.e("Socket Type: " + mSocketType + " create() failed " + e);
			}
			
			mmSocket = tmp;
			mState = STATE_CONNECTING;
		}
		
		@Override
		public void run() {
			Timber.i("BEGIN mConnectThread SocketType: " + mSocketType);
			String name = "ConnectThread " + mSocketType;
			
			// Always cancel discovery because it will slow down a connection
			mAdapter.cancelDiscovery();
			
			// Make a connection to the BluetoothSocket
			try {
				// This is a blocking call and will only return on a
				// successful connection or an exception
				mmSocket.connect();
				
			} catch (IOException e) {
				// Close the socket
				try {
					mmSocket.close();
				} catch (IOException e2) {
					Timber.e("unable to close() " + mSocketType + " socket during connection failure " + e2);
				}
				
				connectionFailed();
				return;
			}
			
			// Reset the ConnectThread because we're done
			synchronized(this) {
				mConnectThread = null;
			}
			
			// Start the connected thread
			connected(mmSocket, mmDevice, mSocketType);
		}
		
		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
				Timber.e("close() of connect " + mSocketType + " socket failed " + e);
			}
			
		}
	}
	
	/**
	 * This thread runs during a connection with a remote device.
	 * It handles all incoming and outgoing transmissions.
	 */
	private class ConnectedThread extends Thread {
		
		private String socketType;
		private BluetoothSocket mmSocket;
		private InputStream mmInStream;
		private OutputStream mmOutStream;
		
		public ConnectedThread(BluetoothSocket mmSocket, String socketType) {
			super();
			Timber.d("create ConnectedThread: " + socketType);
			this.mmSocket = mmSocket;
			this.socketType = socketType;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;
			
			// Get the BluetoothSocket input and output streams
			try {
				tmpIn = mmSocket.getInputStream();
				tmpOut = mmSocket.getOutputStream();
			} catch (IOException e) {
				Timber.e("temp sockets not created " + e);
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
			mState = STATE_CONNECTED;
		}
		
		@Override
		public void run() {
			Timber.i("BEGIN mConnectedThread");
			byte[] buffer = new byte[1024];
			Integer bytes;
			
			// Keep listening to the InputStream while connected
			while (mState == STATE_CONNECTED) {
				try {
					// Read from the InputStream
					bytes = mmInStream.read(buffer);
					
					// Send the obtained bytes to the UI Activity
					mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
				} catch (IOException e) {
					Timber.e("disconnected | " + e);
					connectionLost();
					break;
				}
				
			}
		}
		
		/**
		 * Write to the connected OutStream.
		 
		 * @param buffer The bytes to write
		 */
		public void write(byte[] buffer) {
			try {
				mmOutStream.write(buffer);
				
				// Share the sent message back to the UI Activity
				mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
			} catch (IOException e) {
				Timber.e("Exception during write | " + e);
			}
			
		}
		
		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
				Timber.e("close() of connect socket failed | " + e);
			}
		}
	}
	
}
