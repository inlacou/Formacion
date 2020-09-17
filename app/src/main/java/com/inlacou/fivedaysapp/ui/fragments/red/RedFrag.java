package com.inlacou.fivedaysapp.ui.fragments.red;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.fragments.BaseFragment;

import timber.log.Timber;

public class RedFrag extends BaseFragment {
	
	private RedFragMdl model = new RedFragMdl();
	private RedFragCtrl controller = new RedFragCtrl(this, model);
	
	public ImageButton ibDialog = null;
	public CheckBox cbHandleClick = null;
	
	public String getTitle() {
		return "RED";
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Timber.d("onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_red, container, false);
		
		initialize(rootView);
		
		populate();
		
		setListeners();
		
		if(savedInstanceState!=null) {
			model.handleFragmentDialogButtonClick = savedInstanceState.getBoolean("handleFragmentDialogButtonClick");
			populate();
		}
		
		return rootView;
	}
	
	private void initialize(View rootView) {
		ibDialog = rootView.findViewById(R.id.ib_dialog);
		cbHandleClick = rootView.findViewById(R.id.cb_handle_click);
	}
	
	private void populate() {
		cbHandleClick.setChecked(model.handleFragmentDialogButtonClick);
	}
	
	private void setListeners() {
		cbHandleClick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				model.handleFragmentDialogButtonClick = b;
			}
		});
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
	
	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		outState.putBoolean("handleFragmentDialogButtonClick", model.handleFragmentDialogButtonClick);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		
		if(savedInstanceState!=null) {
			model.handleFragmentDialogButtonClick = savedInstanceState.getBoolean("handleFragmentDialogButtonClick");
			populate();
		}
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(savedInstanceState!=null) {
			model.handleFragmentDialogButtonClick = savedInstanceState.getBoolean("handleFragmentDialogButtonClick");
			populate();
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		Timber.d("onResume");
	}
	
	@Override
	public void onStop() {
		Timber.d("onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		Timber.d("onDestroy");
		super.onDestroy();
	}
	
	@Override
	public void onPause() {
		Timber.d("onPause");
		super.onPause();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Timber.d("onStart");
	}
	
	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		Timber.d("onAttach");
	}
	
	@Override
	public void onDetach() {
		Timber.d("onDetach");
		super.onDetach();
	}
	
	@Override
	public void onDestroyView() {
		Timber.d("onDestroyView");
		super.onDestroyView();
	}
}
