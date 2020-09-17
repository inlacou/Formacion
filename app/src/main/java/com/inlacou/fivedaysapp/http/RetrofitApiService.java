package com.inlacou.fivedaysapp.http;

import com.inlacou.fivedaysapp.business.PokemonListResponse;
import com.inlacou.fivedaysapp.business.PokemonStub;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApiService {
	
	@GET("pokemon")
	Call<PokemonListResponse> getPokemonAsync(@Query("offset") int offset, @Query("limit") int limit);
	
}
