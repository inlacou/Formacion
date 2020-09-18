package com.inlacou.fivedaysapp.ui.activities.pokemon.detail;

import com.inlacou.fivedaysapp.business.Pokemon;

public class PokemonDetailActMdl {

	protected Pokemon pokemon;
	protected Boolean usePicasso = false;
	
	public PokemonDetailActMdl(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
	public PokemonDetailActMdl(Pokemon pokemon, Boolean usePicasso) {
		this.pokemon = pokemon;
		this.usePicasso = usePicasso;
	}
	
}
