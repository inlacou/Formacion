package com.inlacou.fivedaysapp.ui.fragments.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inlacou.fivedaysapp.R;

public class ListFrag extends Fragment {
	
	protected SwipeRefreshLayout srl = null;
	protected RecyclerView rv = null;
	
	private ListFragMdl model = new ListFragMdl();
	private ListFragCtrl controller = new ListFragCtrl(this, model);
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_pokemon_list, container, false);
		
		initialize(rootView);
		
		populate();
		
		setListeners();
		
		return rootView;
	}
	
	private void initialize(View rootView) {
		srl = rootView.findViewById(R.id.srl);
		rv = rootView.findViewById(R.id.rv);
	}
	
	private void populate() {
		controller.populate();
	}
	
	private void setListeners() {
		srl.setOnRefreshListener(() -> {
			controller.update();
		});
	}
	
	protected void onLoaded() {
		srl.setRefreshing(false);
	}
	
	protected void onStartLoad() {
		srl.setRefreshing(true);
	}
	
}