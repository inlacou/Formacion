package com.inlacou.fivedaysapp.ui.fragments.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SensorsFrag extends BaseFragment {
	
	private SensorsFragMdl model = new SensorsFragMdl();
	private SensorsFragCtrl controller = new SensorsFragCtrl(this, model);
	
	@BindView(R.id.tv_sensor) TextView tvSensor;
	@BindView(R.id.bt_start_stop)
	Button btStartStop;
	
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
}
