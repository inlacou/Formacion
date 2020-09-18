package com.inlacou.fivedaysapp.http;

import com.inlacou.fivedaysapp.business.Pokemon;
import com.inlacou.fivedaysapp.business.PokemonListResponse;
import com.inlacou.fivedaysapp.business.PokemonStub;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApiService {
	
	@GET("pokemon")
	Call<PokemonListResponse> getPokemonAsync(@Query("offset") int offset, @Query("limit") int limit);
	
	@GET("pokemon/{id}/")
	Call<Pokemon> getPokemonAsync(@Path("id") int id);
	
	//Get with auth token
	@GET("pokemon")
	Call<PokemonListResponse> getPokemonAsync(@Header("Authorization") String authorization, @Query("offset") int offset, @Query("limit") int limit); //Wont work, the API does not allow something like this
	
	//Send post with body
	@POST("pokemon")
	Call<Pokemon> createPokemon(@Body Pokemon pokemon); //Wont work, the API does not allow something like this
	
	//Send post with form-encoded data
	@FormUrlEncoded
	@POST("pokemon")
	Call<Pokemon> updatePokemon(@Field("name") String name); //Wont work, the API does not allow something like this
	
	//Send post with multipart data
	@Multipart
	@PUT("pokemon")
	Call<Pokemon> updatePokemon(@Part("image-front") RequestBody imageFront, @Part("image-back") RequestBody imageBack); //Wont work, the API does not allow something like this
	
}
