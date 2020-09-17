package com.inlacou.fivedaysapp.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiCtrl {
	
	private RetrofitApiCtrl() {}
	
	private static Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://pokeapi.co/api/v2/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();
	
	public static RetrofitApiService service = retrofit.create(RetrofitApiService.class);
	
}
