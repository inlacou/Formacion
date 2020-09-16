package com.inlacou.fivedaysapp.ui.activities.frame;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.fragments.red.RedFrag;

public class FrameAct extends AppCompatActivity {
	
	private FrameActMdl model;
	private FrameActCtrl controller;
	private Fragment myFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame);
		
		if (savedInstanceState != null) {
			//Restore the fragment's instance
			myFragment = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
		}
		
		initialize();
		
		populate();
		
		setListeners();
	}
	
	private void initialize() {
		if(myFragment==null) myFragment = new RedFrag();
	}
	
	private void populate() {
		final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fl, myFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	private void setListeners() {
	
	}
	
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "myFragmentName", myFragment);
	}
}
