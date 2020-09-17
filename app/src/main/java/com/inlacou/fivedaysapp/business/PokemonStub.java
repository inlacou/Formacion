package com.inlacou.fivedaysapp.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokemonStub {
	
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("url")
	@Expose
	public String url;
	
	public PokemonStub(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
}
