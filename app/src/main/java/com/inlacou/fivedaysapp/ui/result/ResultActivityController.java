package com.inlacou.fivedaysapp.ui.result;

class ResultActivityController {
	
	private ResultActivity view;
	private ResultActivityModel model;
	
	public ResultActivityController(ResultActivity view, ResultActivityModel model) {
		this.view = view;
		this.model = model;
	}
	
	public void onButtonEndClick() {
		if(model.currentValue==null) {
			view.finishNok();
		} else {
			view.finishOk();
		}
	}
}
