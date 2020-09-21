package com.inlacou.fivedaysapp.ui.activities.pokemon.detail;

import com.inlacou.fivedaysapp.business.Pokemon;

public class PokemonDetailActMdl {

	public Pokemon pokemon;
	public Boolean usePicasso = false;
	public boolean isFavorite = false;
	
	public PokemonDetailActMdl(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
	public PokemonDetailActMdl(Pokemon pokemon, Boolean usePicasso) {
		this.pokemon = pokemon;
		this.usePicasso = usePicasso;
	}
	
}
