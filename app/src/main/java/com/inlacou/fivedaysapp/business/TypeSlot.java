package com.inlacou.fivedaysapp.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeSlot {
	
	@SerializedName("slot")
	@Expose
	public Integer slot;
	@SerializedName("type")
	@Expose
	public Type type;
	
}