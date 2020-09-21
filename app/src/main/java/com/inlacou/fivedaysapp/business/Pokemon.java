package com.inlacou.fivedaysapp.business;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokemon implements Comparable<Pokemon> {
	
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
	@SerializedName("sprites")
	@Expose
	public Sprites sprites = null;
	@SerializedName("weight")
	@Expose
	public Integer weight;
	
	
	
	@Override
	public String toString() {
		return "Pokemon {" +
				"id=" + id +
				", name='" + name + '\'' +
				", height=" + height +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Pokemon)) return false;
		Pokemon pokemon = (Pokemon) o;
		return id == pokemon.id &&
				height == pokemon.height &&
				Objects.equals(name, pokemon.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name, height);
	}
	
	public Pokemon(int id, String name, int height) {
		this.id = id;
		this.name = name;
		this.height = height;
	}
	
	public Pokemon(int id, String name, int height, Sprites sprites) {
		this.id = id;
		this.name = name;
		this.height = height;
		this.sprites = sprites;
	}
	
	public Pokemon(int id, String name, int height, String frontImage, String backImage) {
		this.id = id;
		this.name = name;
		this.height = height;
		this.sprites = new Sprites(frontImage, backImage);
	}
	
	public Integer getId() {
		return id;
	}
	
	@Override
	public int compareTo(Pokemon pokemon2) {
		Pokemon pokemon1 = this;
		if(pokemon1.id>pokemon2.id) return 1; else if(pokemon1.id.equals(pokemon2.id)) return 0; else return -1;
	}
}