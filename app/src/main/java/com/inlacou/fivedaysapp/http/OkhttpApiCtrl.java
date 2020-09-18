package com.inlacou.fivedaysapp.http;

import java.io.File;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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

	public void getPokemonAsyncAuth(int offset, int limit, Callback callback) {
		Request request = new Request.Builder()
				.url("https://pokeapi.co/api/v2/pokemon?limit="+limit+"&offset="+offset)
				.addHeader("oauth-token", "your-oauth-token-here")
				.build();
		
		Call call = client.newCall(request);
		call.enqueue(callback);
	}

	public void getPokemonAsync(int id, Callback callback) {
		Request request = new Request.Builder()
				.url("https://pokeapi.co/api/v2/pokemon/" + id + "/")
				.build();
		
		Call call = client.newCall(request);
		call.enqueue(callback);
	}

	public void createPokemonAsyncForm(String name, Callback callback) {
		RequestBody requestBody = new FormBody.Builder()
				.add("name", name)
				.build();
		
		Request request = new Request.Builder()
				.url("https://pokeapi.co/api/v2/pokemon")
				.post(requestBody)
				.build();
		
		Call call = client.newCall(request);
		call.enqueue(callback);
	}
	
	public void createPokemonAsyncJson(String name, Callback callback) {
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody requestBody = RequestBody.create(JSON, "{\"name\": "+name+"}");
		
		Request request = new Request.Builder()
				.url("https://pokeapi.co/api/v2/pokemon")
				.post(requestBody)
				.build();
		
		Call call = client.newCall(request);
		call.enqueue(callback);
	}
	
	public void updatePokemonAsync(File imageFront, File imageBack, Callback callback) {
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("somParam", "someValue")
				.addFormDataPart("imageFront", imageFront.getName(), RequestBody.create(MediaType.parse("text/plain"), imageFront))
				.addFormDataPart("imageBack", imageBack.getName(), RequestBody.create(MediaType.parse("text/plain"), imageBack))
				.build();
		
		Request request = new Request.Builder()
				.url("https://pokeapi.co/api/v2/pokemon")
				.put(requestBody)
				.build();
		
		Call call = client.newCall(request);
		call.enqueue(callback);
	}

}
