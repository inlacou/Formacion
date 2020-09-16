package com.inlacou.fivedaysapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inlacou.fivedaysapp.R;

public class RedFrag extends Fragment {
	
	private RedFragMdl model = new RedFragMdl();
	private RedFragCtrl controller = new RedFragCtrl(this, model);
	
	public ImageButton ibDialog = null;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_red, container, false);
		
		initialize(rootView);
		
		populate();
		
		setListeners();
		
		return rootView;
	}
	
	private void initialize(View rootView) {
		ibDialog = rootView.findViewById(R.id.ib_dialog);
	}
	
	private void populate() {
	}
	
	private void setListeners() {
		ibDialog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controller.onRedButtonClick();
			}
		});
	}
	
	public void showToast(String s) {
		Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
	}
}
