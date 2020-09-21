package com.inlacou.fivedaysapp.ui.fragments.pokemon.list.liked;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.fragments.BaseFragment;

import butterknife.BindView;

public class LikedPokemonListFrag extends BaseFragment {
	
	@BindView(R.id.srl) SwipeRefreshLayout srl;
	@BindView(R.id.rv) RecyclerView rv;
	
	private LikedPokemonListFragMdl model = new LikedPokemonListFragMdl();
	private LikedPokemonListFragCtrl controller = new LikedPokemonListFragCtrl(this, model);
	
	@Override
	public String getTitle() {
		return "Pokemon list";
	}
	
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
