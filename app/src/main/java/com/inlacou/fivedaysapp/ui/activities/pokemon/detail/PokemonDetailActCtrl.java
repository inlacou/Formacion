package com.inlacou.fivedaysapp.ui.activities.pokemon.detail;

import com.inlacou.fivedaysapp.business.Pokemon;
import com.inlacou.fivedaysapp.datapersistence.sharedpreferences.SharedPrefManager;
import com.inlacou.fivedaysapp.datapersistence.sqlite.PokemonDb;

import java.util.Objects;

public class PokemonDetailActCtrl {
	
	private PokemonDetailAct view;
	private PokemonDetailActMdl model;
	
	public PokemonDetailActCtrl(PokemonDetailAct view, PokemonDetailActMdl model) {
		this.view = view;
		this.model = model;
	}
	
	protected void populate() {
		Pokemon pokemon = PokemonDb.retrieveLikedPokemon(view, model.pokemon.id);
		view.setLiked(pokemon!=null);
		Integer favPokemonId = SharedPrefManager.getFavoritePokemonId(view);
		model.isFavorite = Objects.equals(favPokemonId, model.pokemon.id);
		view.updateFavoriteStatus();
	}
	
	public void onLikedChange(boolean b) {
		if(b) {
			PokemonDb.insertLikedPokemon(view, model.pokemon);
		} else {
			PokemonDb.deleteLikedPokemon(view, model.pokemon);
		}
	}
	
	public void onFavoritedChange(boolean b) {
		if(b) {
			SharedPrefManager.setFavoritePokemonId(view, model.pokemon.id);
			model.isFavorite = true;
		} else {
			SharedPrefManager.setFavoritePokemonId(view, null);
			model.isFavorite = false;
		}
	}
}
