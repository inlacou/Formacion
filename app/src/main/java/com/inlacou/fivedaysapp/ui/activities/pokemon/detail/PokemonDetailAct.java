package com.inlacou.fivedaysapp.ui.activities.pokemon.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.inlacou.fivedaysapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonDetailAct extends AppCompatActivity {
	
	private PokemonDetailActMdl model = null;
	private PokemonDetailActCtrl controller = null;
	
	@BindView(R.id.tv_name) TextView tvName;
	@BindView(R.id.tv_height) TextView tvHeight;
	
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
	
	public void populate() {}
	
	public void setListeners() {}
}
