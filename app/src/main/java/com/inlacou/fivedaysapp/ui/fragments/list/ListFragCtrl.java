package com.inlacou.fivedaysapp.ui.fragments.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.inlacou.fivedaysapp.adapters.PokemonStubRvAdapter;
import com.inlacou.fivedaysapp.business.Pokemon;
import com.inlacou.fivedaysapp.business.PokemonListResponse;
import com.inlacou.fivedaysapp.business.PokemonStub;
import com.inlacou.fivedaysapp.http.OkhttpApiCtrl;
import com.inlacou.fivedaysapp.http.RetrofitApiCtrl;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import timber.log.Timber;

public class ListFragCtrl {
	
	private static int PAGE_SIZE = 100;
	
	private int page = 0;
	private PokemonStubRvAdapter adapter;
	
	private ListFrag view;
	private ListFragMdl model;
	
	public ListFragCtrl(ListFrag view, ListFragMdl model) {
		this.view = view;
		this.model = model;
		adapter = new PokemonStubRvAdapter(model.list, (view1, index, section) -> {
			OkhttpApiCtrl.instance.getPokemonAsync(section.url, new Callback() {
				@Override
				public void onResponse(Call call, Response response) throws IOException {
					String body = response.body().string();
					Pokemon pokemon = new Gson().fromJson(body, Pokemon.class);
					
				}
				@Override
				public void onFailure(Call call, IOException e) {
					Timber.d("onFailure | exception: " + e.getMessage());
					//TODO show error
				}
			});
		});
	}

	public void populate() {
		view.rv.setAdapter(adapter);
		view.rv.setLayoutManager(new LinearLayoutManager(view.getActivity(), RecyclerView.VERTICAL, false));
		update();
	}
	
	public void update() {
		page = 0;
		model.list.clear();
		load();
	}
	
	public void load() {
		view.onStartLoad();
		if(model.useRetrofit) {
			RetrofitApiCtrl.service.getPokemonAsync(page * PAGE_SIZE, PAGE_SIZE).enqueue(new retrofit2.Callback<PokemonListResponse>() {
				@Override
				public void onResponse(retrofit2.Call<PokemonListResponse> call, retrofit2.Response<PokemonListResponse> response) {
					model.list.addAll(response.body().results);
					page++;
					view.getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
					view.onLoaded();
				}
				@Override
				public void onFailure(retrofit2.Call<PokemonListResponse> call, Throwable t) {
					Timber.d("onFailure | exception: " + t.getMessage());
					//TODO show error
				}
			});
		} else {
			OkhttpApiCtrl.instance.getPokemonAsync(page * PAGE_SIZE, PAGE_SIZE, new Callback() {
				@Override
				public void onFailure(@NotNull Call call, @NotNull IOException e) {
					Timber.d("onFailure | exception: " + e.getMessage());
					//TODO show error
				}
				@Override
				public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
					String body = response.body().string();
					try {
						JSONObject json = new JSONObject(body);
						JSONArray results = json.getJSONArray("results");
						for (int i = 0; i < results.length(); i++) {
							JSONObject object = results.getJSONObject(i);
							model.list.add(new PokemonStub(object.getString("name"), object.getString("url")));
						}
						page++;
					} catch (JSONException e) {
						//TODO show error
						e.printStackTrace();
					}
					view.getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
					view.onLoaded();
				}
			});
		}
	}
	
	public void onPokemonListItemClick() {
	
	}
	
}
