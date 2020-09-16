package com.inlacou.fivedaysapp.ui.activities.frame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.fragments.RedFrag;

public class FrameAct extends AppCompatActivity {
	
	private FrameActMdl model;
	private FrameActCtrl controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame);
		
		initialize();
		
		populate();
		
		setListeners();
	}
	
	private void initialize() {
	}
	
	private void populate() {
		final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fl, new RedFrag());
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	private void setListeners() {
	
	}
	
	
}
