package com.inlacou.fivedaysapp.ui.activities.pokemon.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.activities.BaseAct;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonDetailAct extends BaseAct {
	
	public PokemonDetailActMdl model = null; //model should be inmutable, but...
	private PokemonDetailActCtrl controller = null;
	
	@BindView(R.id.tv_name) TextView tvName;
	@BindView(R.id.tv_height) TextView tvHeight;
	@BindView(R.id.iv_front) ImageView ivFront;
	@BindView(R.id.cb_liked) CheckBox cbLiked;
	@BindView(R.id.cb_favorite) public CheckBox cbFavorited;
	
	public static void navigate(Activity activity, PokemonDetailActMdl model) {
		activity.startActivity(intent(activity, model));
	}
	
	public static Intent intent(Context context, PokemonDetailActMdl model) {
		Intent intent = new Intent(context, PokemonDetailAct.class);
		intent.putExtra("model", new Gson().toJson(model));
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pokemon_detail);
		
		getIntentData();
		
		initialize();
		
		configureActionBar();
		
		populate();
		
		setListeners();
	}
	
	public void getIntentData() {
		model = new Gson().fromJson(getIntent().getExtras().getString("model"), PokemonDetailActMdl.class);
		controller = new PokemonDetailActCtrl(this, model);
	}
	
	public void initialize() {
		ButterKnife.bind(this);
	}
	
	private void configureActionBar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
			if(getSupportActionBar()!=null) {
				getSupportActionBar().setTitle(model.pokemon.name);
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
	}
	
	public void populate() {
		tvName.setText(model.pokemon.name);
		tvHeight.setText(model.pokemon.height.toString());
		if(model.usePicasso) {
			Picasso.get().load(model.pokemon.sprites.frontDefault).into(ivFront);
		} else {
			Glide.with(this).load(model.pokemon.sprites.frontDefault).into(ivFront);
		}
		controller.populate();
	}
	
	public void setListeners() {
		cbLiked.setOnCheckedChangeListener((compoundButton, b) -> {
			controller.onLikedChange(b);
		});
		cbFavorited.setOnCheckedChangeListener((compoundButton, b) -> {
			controller.onFavoritedChange(b);
		});
	}
	
	protected void setLiked(Boolean liked) {
		cbLiked.setChecked(liked);
	}
	
	protected void updateFavoriteStatus() {
		cbFavorited.setChecked(model.isFavorite);
	}
	
}
