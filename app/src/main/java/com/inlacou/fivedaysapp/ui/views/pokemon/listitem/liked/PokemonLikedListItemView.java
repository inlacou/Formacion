package com.inlacou.fivedaysapp.ui.views.pokemon.listitem.liked;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.inlacou.fivedaysapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonLikedListItemView extends FrameLayout {
	
	@BindView(R.id.rl) View rl = null;
	@BindView(R.id.tv_id) TextView tvId;
	@BindView(R.id.tv_title) TextView tvTitle;
	@BindView(R.id.iv_front) ImageView ivFront;
	@BindView(R.id.iv_back) ImageView ivBack;
	
	private PokemonLikedListItemViewMdl model = new PokemonLikedListItemViewMdl(null);
	private PokemonLikedListItemViewCtrl controller = new PokemonLikedListItemViewCtrl(this, model);
	
	public void setModel(PokemonLikedListItemViewMdl model) {
		this.model = model;
		controller.model = model;
		populate();
	}
	
	public PokemonLikedListItemView(Context context) {
		super(context);
		initialize();
		populate();
		setListeners();
	}
	
	public PokemonLikedListItemView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initialize();
		populate();
		setListeners();
	}
	
	public PokemonLikedListItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize();
		populate();
		setListeners();
	}
	
	private void initialize() {
		View rootView = View.inflate(getContext(), R.layout.view_list_pokemon_liked, this);
		initialize(rootView);
	}
	
	public void initialize(View rootView) {
		ButterKnife.bind(this, rootView);
	}
	
	private void populate() {
		if(model.pokemon!=null) {
			tvId.setText(getResources().getString(R.string.Hashtag_variable, model.pokemon.id));
			tvTitle.setText(model.pokemon.name);
			if(model.pokemon.sprites!=null) {
				Picasso.get().load(model.pokemon.sprites.frontDefault).into(ivFront);
				Glide.with(this).load(model.pokemon.sprites.backDefault).into(ivBack);
			}
		}
	}
	
	private void setListeners() {
		rl.setOnClickListener(view -> controller.onClick());
	}
	
}
