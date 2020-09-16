package com.inlacou.fivedaysapp.ui.fragments.blue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inlacou.fivedaysapp.R;

public class BlueFrag extends Fragment {
	
	private BlueFragMdl model = new BlueFragMdl();
	private BlueFragCtrl controller = new BlueFragCtrl(this, model);
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blue, container, false);
		
		initialize(rootView);
		
		populate();
		
		setListeners();
		
		return rootView;
	}
	
	private void initialize(View rootView) {
	}
	
	private void populate() {
	}
	
	private void setListeners() {
	}
	
}
