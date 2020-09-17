package com.inlacou.fivedaysapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.business.PokemonStub;

import java.util.List;

public class PokemonStubRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	
	private List<PokemonStub> itemList;
	public Callback callback;
	
	public PokemonStubRvAdapter(List<PokemonStub> itemList, Callback callback) {
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
		return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
				R.layout.recyclerview_item_row_pokemon_stub, parent, false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
		MyViewHolder myViewHolder = (MyViewHolder) holder;
		myViewHolder.tv.setText(itemList.get(position).name);
		myViewHolder.itemView.setOnClickListener(view -> callback.onClick(view, position, itemList.get(position)));
	}
	
	@Override
	public int getItemCount() { return itemList.size(); }
	
	static class MyViewHolder extends RecyclerView.ViewHolder {

		TextView tv = itemView.findViewById(R.id.title);

		public MyViewHolder(View itemView) {
			super(itemView);
		}
	}
	
	public interface Callback {
		void onClick(View view, int index, PokemonStub section);
	}
}
