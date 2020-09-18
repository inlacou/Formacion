package com.inlacou.fivedaysapp.ui.activities.pokemon.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.ui.activities.BaseAct;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonDetailAct extends BaseAct {
	
	private PokemonDetailActMdl model = null;
	private PokemonDetailActCtrl controller = null;
	
	@BindView(R.id.tv_name) TextView tvName;
	@BindView(R.id.tv_height) TextView tvHeight;
	@BindView(R.id.iv_front) ImageView ivFront;
	@BindView(R.id.iv_back) ImageView ivBack;
	@BindView(R.id.cb_liked) CheckBox cbLiked;
	@BindView(R.id.cb_favorite) CheckBox cbFavorited;
	
	public static void navigate(Activity activity, PokemonDetailActMdl model) {
		Intent intent = new Intent(activity, PokemonDetailAct.class);
		intent.putExtra("model", new Gson().toJson(model));
		activity.startActivity(intent);
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
			Picasso.get().load(model.pokemon.sprites.backDefault).into(ivBack);
		} else {
			Glide.with(this).load(model.pokemon.sprites.frontDefault).into(ivFront);
			Glide.with(this).load(model.pokemon.sprites.backDefault).into(ivBack);
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
	
	protected void setFavorited(Boolean liked) {
		cbFavorited.setChecked(liked);
	}
	
}
