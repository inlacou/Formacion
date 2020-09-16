package com.inlacou.fivedaysapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.inlacou.fivedaysapp.ui.result.ResultActivity;

class MainActivityController {
	
	public static int REQUEST_CODE_RESULT = 1337;
	
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
	
	public void onButtonStartNormalClick() {
		view.startActivity(new Intent(view, ResultActivity.class));
	}
	
	public void onButtonStartResultClick() {
		Intent intent = new Intent(view, ResultActivity.class);
		if(model.sendDataOnStartActivityForResult) {
			intent.putExtra("data", "some string");
		}
		view.startActivityForResult(intent, REQUEST_CODE_RESULT);
	}
	
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if(requestCode!=REQUEST_CODE_RESULT) {
			return;
		}
		if(resultCode==Activity.RESULT_OK) {
			view.showToast("RESULT_OK");
		}else if(resultCode==Activity.RESULT_CANCELED) {
			view.showToast("RESULT_CANCELED");
		}
	}
	
	public void onButtonOpenBrowserClick() {
		if(model.openOnBrowser) {
			view.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.url)));
		} else {
			view.loadUrlOnWebView(model.url);
		}
	}
	
	public void onCheckBoxOpenBrowserChecked() {
		model.openOnBrowser = true;
	}
	
	public void onCheckBoxOpenWebViewChecked() {
		model.openOnBrowser = false;
	}
}
