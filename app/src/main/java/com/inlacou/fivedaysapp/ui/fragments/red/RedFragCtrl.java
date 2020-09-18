package com.inlacou.fivedaysapp.ui.fragments.red;

import android.content.Intent;

import com.inlacou.fivedaysapp.ui.fragments.dialog.DialogFragment;

class RedFragCtrl {
	
	private RedFrag view;
	private RedFragMdl model;
	
	public RedFragCtrl(RedFrag view, RedFragMdl model) {
		this.view = view;
		this.model = model;
	}
	
	public void onRedButtonClick() {
		final DialogFragment dialog = new DialogFragment(new DialogFragment.Callback() {
			@Override
			public boolean onButtonClick() {
				if(model.handleFragmentDialogButtonClick) {
					view.showToast("we handle the dialog button click showing this message");
					return true;
				} else return false;
			}
		});
		dialog.show(view.getActivity().getSupportFragmentManager(), "blue fragment as dialog");
		
		Intent intent = new Intent();
		intent.setAction(view.getActivity().getPackageName()+".CUSTOM_INTENT");
		view.getActivity().sendBroadcast(intent);
	}
}
