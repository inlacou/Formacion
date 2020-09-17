package com.inlacou.fivedaysapp.ui.fragments.list;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inlacou.fivedaysapp.adapters.PokemonStubRvAdapter;
import com.inlacou.fivedaysapp.business.PokemonStub;
import com.inlacou.fivedaysapp.http.ApiCtrl;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
			//TODO open detail view, for example
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
		ApiCtrl.instance.getPokemonAsync(page, PAGE_SIZE, new Callback() {
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
					for (int i=0; i<results.length(); i++) {
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
