package com.inlacou.fivedaysapp.ui.fragments.pokemon.list.liked;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inlacou.fivedaysapp.adapters.PokemonLikedRvAdapter;
import com.inlacou.fivedaysapp.business.Pokemon;
import com.inlacou.fivedaysapp.datapersistence.sqlite.PokemonDb;

import java.util.ArrayList;
import java.util.Comparator;

public class LikedPokemonListFragCtrl {
	
	private static int PAGE_SIZE = 100;
	
	private int page = 0;
	private PokemonLikedRvAdapter adapter;
	
	private LikedPokemonListFrag view;
	private LikedPokemonListFragMdl model;
	
	public LikedPokemonListFragCtrl(LikedPokemonListFrag view, LikedPokemonListFragMdl model) {
		this.view = view;
		this.model = model;
		adapter = new PokemonLikedRvAdapter(model.list, (view1, index, section) -> {
			//Do nothing
		});
	}
	
	public void populate() {
		view.rv.setAdapter(adapter);
		view.rv.setLayoutManager(new LinearLayoutManager(view.getActivity(), RecyclerView.VERTICAL, false));
		update();
	}
	
	public void update() {
		page = 0;
		model.list.clear();
		load();
	}
	
	public void load() {
		view.onStartLoad();
		ArrayList<Pokemon> pokemonList = PokemonDb.retrieveLikedPokemon(view.getContext());
		pokemonList.sort((pokemon, t1) -> pokemon.compareTo(t1));
		model.list.clear(); //There is no pagination in this case.
		model.list.addAll(pokemonList);
		adapter.notifyDataSetChanged();
		view.onLoaded();
	}
	
}
