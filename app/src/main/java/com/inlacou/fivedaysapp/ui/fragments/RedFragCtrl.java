package com.inlacou.fivedaysapp.ui.fragments;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.inlacou.fivedaysapp.R;

class RedFragCtrl {
	
	private RedFrag view;
	private RedFragMdl model;
	
	public RedFragCtrl(RedFrag view, RedFragMdl model) {
		this.view = view;
		this.model = model;
	}
	
	public void onRedButtonClick() {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(view.getActivity());
		dialog.setTitle("Red button");
		dialog.setMessage("You clicked the red button!");
		dialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				view.showToast("Glad you acknowledged");
			}
		});
		dialog.setNeutralButton(R.string.Maybe, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				view.showToast("Reality is often disappointing...");
			}
		});
		dialog.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				view.showToast("Yes, you did!");
			}
		});
		dialog.show();
	}
}
