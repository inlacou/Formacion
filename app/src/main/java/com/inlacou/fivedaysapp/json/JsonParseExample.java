package com.inlacou.fivedaysapp.json;

import com.google.gson.Gson;
import com.inlacou.fivedaysapp.business.Pokemon;

import timber.log.Timber;

public class JsonParseExample {
	
	static String EXAMPLE_JSON = "{\n" +
			"  \"base_experience\": 64,\n" +
			"  \"height\": 7,\n" +
			"  \"id\": 1,\n" +
			"  \"is_default\": true,\n" +
			"  \"location_area_encounters\": \"https://pokeapi.co/api/v2/pokemon/1/encounters\",\n" +
			"  \"name\": \"bulbasaur\",\n" +
			"  \"order\": 1,\n" +
			"  \"types\": [\n" +
			"    {\n" +
			"      \"slot\": 1,\n" +
			"      \"type\": {\n" +
			"        \"name\": \"grass\",\n" +
			"        \"url\": \"https://pokeapi.co/api/v2/type/12/\"\n" +
			"      }\n" +
			"    },\n" +
			"    {\n" +
			"      \"slot\": 2,\n" +
			"      \"type\": {\n" +
			"        \"name\": \"poison\",\n" +
			"        \"url\": \"https://pokeapi.co/api/v2/type/4/\"\n" +
			"      }\n" +
			"    }\n" +
			"  ],\n" +
			"  \"weight\": 69\n" +
			"}";

	public static Pokemon parse() {
		Pokemon pokemon = new Gson().fromJson(EXAMPLE_JSON, Pokemon.class);
		Timber.d("parsed json to pokemon: " + pokemon.name);
		return pokemon;
	}
	
}
