package com.inlacou.fivedaysapp;

class MainActivityController {
	
	private MainActivity view;
	private MainActivityModel model;
	
	public MainActivityController(MainActivity view, MainActivityModel model) {
		this.view = view;
		this.model = model;
	}
	
	public void onButtonMoreClick() {
		model.currentValue++;
		view.updateValue();
	}
	
	public void onButtonLessClick() {
		model.currentValue--;
		view.updateValue();
	}
}
