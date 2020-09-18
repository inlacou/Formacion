package com.inlacou.fivedaysapp.business;

import java.util.Objects;

public class Pokemon {
	
	public int id = 0;
	public String name = null;
	public int height = 0;
	
	public Pokemon(int id, String name, int height) {
		this.id = id;
		this.name = name;
		this.height = height;
	}
	
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
}
