package com.inlacou.fivedaysapp.ui.fragments.sensors;

public class SensorsFragCtrl {
	
	private final SensorsFrag view;
	private final SensorsFragMdl model;
	
	public SensorsFragCtrl(SensorsFrag view, SensorsFragMdl model) {
		this.view = view;
		this.model = model;
	}
	
	public void onStartStopClick() {
		if(model.listening) {
			view.stopListening();
		} else {
			view.startListening();
		}
		view.updateStartStopButtonStatus();
	}
}
