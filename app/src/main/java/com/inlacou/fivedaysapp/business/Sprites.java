package com.inlacou.fivedaysapp.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sprites {
	
	public Sprites(String frontDefault, String backDefault) {
		this.backDefault = backDefault;
		this.frontDefault = frontDefault;
	}
	
	@SerializedName("back_default")
	@Expose
	public String backDefault;
	
	@SerializedName("front_default")
	@Expose
	public String frontDefault;
	
}
