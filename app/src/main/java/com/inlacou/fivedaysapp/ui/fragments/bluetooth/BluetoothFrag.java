package com.inlacou.fivedaysapp.ui.fragments.bluetooth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BluetoothFrag extends BaseFragment {
	
	@BindView(R.id.srl) SwipeRefreshLayout srl;
	@BindView(R.id.rv) RecyclerView rv;
	@BindView(R.id.btn_dummy_msg) Button btnDummyMsg;
	
	private BluetoothFragMdl model = new BluetoothFragMdl();
	private BluetoothFragCtrl controller = new BluetoothFragCtrl(this, model);
	
	@Override
	public String getTitle() {
		return "Bluetooth device list";
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bluetooth, container, false);
		
		initialize(rootView);
		
		populate();
		
		setListeners();
		
		return rootView;
	}
	
	private void initialize(View rootView) {
		ButterKnife.bind(this, rootView);
	}
	
	private void populate() {
		controller.populate();
	}
	
	private void setListeners() {
		srl.setOnRefreshListener(() -> {
			controller.update();
		});
		btnDummyMsg.setOnClickListener(view -> {
			controller.onSendDummyMessageClick();
		});
	}
	
	protected void onLoaded() {
		srl.setRefreshing(false);
	}
	
	protected void onStartLoad() {
		srl.setRefreshing(true);
	}
	
	protected void toast(String s) {
		Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
	}
}
