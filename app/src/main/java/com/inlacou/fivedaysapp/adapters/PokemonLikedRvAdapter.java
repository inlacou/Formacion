package com.inlacou.fivedaysapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.business.Pokemon;
import com.inlacou.fivedaysapp.ui.views.pokemon.listitem.liked.PokemonLikedListItemView;
import com.inlacou.fivedaysapp.ui.views.pokemon.listitem.liked.PokemonLikedListItemViewMdl;

import java.util.List;

public class PokemonLikedRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	
	private List<Pokemon> itemList;
	public Callback callback;
	
	public PokemonLikedRvAdapter(List<Pokemon> itemList, Callback callback) {
		this.itemList = itemList;
		this.callback = callback;
	}
	
	@Override
	public int getItemViewType(int position) {
		return 0;
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_row_pokemon_liked, parent, false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
		MyViewHolder myViewHolder = (MyViewHolder) holder;
		myViewHolder.view.setModel(new PokemonLikedListItemViewMdl(itemList.get(position)));
		myViewHolder.itemView.setOnClickListener(view -> callback.onClick(view, position, itemList.get(position)));
	}
	
	@Override
	public int getItemCount() { return itemList.size(); }
	
	static class MyViewHolder extends RecyclerView.ViewHolder {
		PokemonLikedListItemView view = itemView.findViewById(R.id.view);

		public MyViewHolder(View itemView) {
			super(itemView);
			view.initialize(itemView);
		}
	}
	
	public interface Callback {
		void onClick(View view, int index, Pokemon pokemon);
	}
}
