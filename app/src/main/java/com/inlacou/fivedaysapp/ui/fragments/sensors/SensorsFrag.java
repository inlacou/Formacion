package com.inlacou.fivedaysapp.ui.fragments.sensors;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SensorsFrag extends BaseFragment {
	
	private static final int CAMERA_PERMISSION_REQUEST_CODE = 775;
	private static final int CAMERA_PHOTO_REQUEST_CODE = 778;
	
	private SensorsFragMdl model = new SensorsFragMdl();
	private SensorsFragCtrl controller = new SensorsFragCtrl(this, model);
	
	@BindView(R.id.tv_sensor) TextView tvSensor;
	@BindView(R.id.bt_start_stop) Button btStartStop;
	@BindView(R.id.iv_photo) ImageView ivPhoto;
	@BindView(R.id.bt_photo) Button btPhoto;
	
	private SensorManager sensorManager;
	private Sensor accelerometer;
	private SensorEventCallback accelerometerListener;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Timber.d("onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_sensors, container, false);
		
		initialize(rootView);
		
		populate();
		
		setListeners();
		
		return rootView;
	}
	
	private void initialize(View rootView) {
		sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		ButterKnife.bind(this, rootView);
	}
	
	private void populate() {
		updateStartStopButtonStatus();
	}
	
	private void setListeners() {
		btStartStop.setOnClickListener(view -> {
			controller.onStartStopClick();
		});
		accelerometerListener = new SensorEventCallback() {
			@Override
			public void onSensorChanged(SensorEvent event) {
				super.onSensorChanged(event);
				String s = "";
				for (int i = 0; i < event.values.length; i++) {
					s += event.values[i] + ", ";
				}
				Timber.d("event.values: %s", s);
				tvSensor.setText(s);
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				super.onAccuracyChanged(sensor, accuracy);
			}
			
			@Override
			public void onFlushCompleted(Sensor sensor) {
				super.onFlushCompleted(sensor);
			}
			
			@Override
			public void onSensorAdditionalInfo(SensorAdditionalInfo info) {
				super.onSensorAdditionalInfo(info);
			}
		};
		btPhoto.setOnClickListener(view -> {
			if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
			} else {
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PHOTO_REQUEST_CODE);
			}
		});
		
	}
	
	protected void updateStartStopButtonStatus() {
		String text;
		if(model.listening) text = "stop"; else text = "start";
		btStartStop.setText(text);
	}
	
	protected void startListening() {
		sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	protected void stopListening() {
		sensorManager.unregisterListener(accelerometerListener);
	}
	
	@Override
	public void onDestroy() {
		sensorManager.unregisterListener(accelerometerListener);
		super.onDestroy();
	}
	
	@Override
	public String getTitle() {
		return "Sensors";
	}
	
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode==CAMERA_PERMISSION_REQUEST_CODE)
		{
			if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PHOTO_REQUEST_CODE);
			} else {
				Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==CAMERA_PHOTO_REQUEST_CODE && resultCode==Activity.RESULT_OK)
		{
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			ivPhoto.setImageBitmap(photo);
		}
	}
}
