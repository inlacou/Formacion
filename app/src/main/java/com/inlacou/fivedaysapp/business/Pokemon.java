package com.inlacou.fivedaysapp.business;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokemon {
	
	@SerializedName("base_experience")
	@Expose
	public Integer baseExperience;
	@SerializedName("height")
	@Expose
	public Integer height;
	@SerializedName("id")
	@Expose
	public Integer id;
	@SerializedName("is_default")
	@Expose
	public Boolean isDefault;
	@SerializedName("location_area_encounters")
	@Expose
	public String locationAreaEncounters;
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("order")
	@Expose
	public Integer order;
	@SerializedName("types")
	@Expose
	public List<TypeSlot> typeSlots = null;
	@SerializedName("weight")
	@Expose
	public Integer weight;
	
}