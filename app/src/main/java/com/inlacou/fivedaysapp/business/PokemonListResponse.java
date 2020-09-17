package com.inlacou.fivedaysapp.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokemonListResponse {
	
	@SerializedName("count")
	@Expose
	public Integer count;
	@SerializedName("next")
	@Expose
	public String next;
	@SerializedName("previous")
	@Expose
	public String previous;
	@SerializedName("results")
	@Expose
	public List<PokemonStub> results = null;
	
}