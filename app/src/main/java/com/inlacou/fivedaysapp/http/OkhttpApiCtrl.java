package com.inlacou.fivedaysapp.http;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkhttpApiCtrl {
	
	public static OkhttpApiCtrl instance = new OkhttpApiCtrl();
	
	private OkhttpApiCtrl() {}
	
	private OkHttpClient client = new OkHttpClient();
	
	public void getPokemonAsync(int offset, int limit, Callback callback) {
		Request request = new Request.Builder()
				.url("https://pokeapi.co/api/v2/pokemon?limit="+limit+"&offset="+offset)
				.build();
		
		Call call = client.newCall(request);
		call.enqueue(callback);
	}

}
