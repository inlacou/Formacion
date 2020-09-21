package com.inlacou.fivedaysapp.ui.fragments.pokemon.list.all;

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

public class AllPokemonListFrag extends BaseFragment {
	
	protected SwipeRefreshLayout srl = null;
	protected RecyclerView rv = null;
	
	private AllPokemonListFragMdl model = new AllPokemonListFragMdl();
	private AllPokemonListFragCtrl controller = new AllPokemonListFragCtrl(this, model);
	
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
		rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				
				if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
					controller.load();
				}
			}
		});
	}
	
	protected void onLoaded() {
		srl.setRefreshing(false);
	}
	
	protected void onStartLoad() {
		srl.setRefreshing(true);
	}
	
}
