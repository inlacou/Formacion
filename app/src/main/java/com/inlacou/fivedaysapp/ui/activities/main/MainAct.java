package com.inlacou.fivedaysapp.ui.activities.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.adapters.SidebarRvAdapter;
import com.inlacou.fivedaysapp.business.MainSection;
import com.inlacou.fivedaysapp.business.Pokemon;
import com.inlacou.fivedaysapp.datapersistence.sharedpreferences.SharedPrefManager;
import com.inlacou.fivedaysapp.datapersistence.sqlite.PokemonDb;
import com.inlacou.fivedaysapp.json.JsonParseExample;
import com.inlacou.fivedaysapp.ui.fragments.BaseFragment;
import com.inlacou.fivedaysapp.ui.fragments.blue.BlueFrag;
import com.inlacou.fivedaysapp.ui.fragments.list.ListFrag;
import com.inlacou.fivedaysapp.ui.fragments.red.RedFrag;
import com.inlacou.fivedaysapp.xml.XmlParseExample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.jodah.xsylum.XsylumException;

import java.util.Arrays;

import timber.log.Timber;

public class MainAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	private DrawerLayout drawerLayout = null;
	private Toolbar toolbar = null;
	private RecyclerView rvDrawer = null;
	private NavigationView navView = null;
	
	protected Fragment fragment = null;
	private ActionBarDrawerToggle toggle = null;
	private SidebarRvAdapter adapterDrawer = null; //Custom Drawer
	
	private MainActMdl model = new MainActMdl();
	private MainActCtrl controller = new MainActCtrl(this, model);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initialize();
		
		populate();
		
		setListeners();
		
		Timber.d("json: " + JsonParseExample.parse().name);
		try {
			Timber.d("xml: " + XmlParseExample.parse().name);
		} catch (XsylumException e) {
			Timber.e("error arised from XmlParseExample: " + e.getMessage());
			e.printStackTrace();
		}
		
		SharedPrefManager.setAuthToken(this, "my auth token");
		Timber.d("retrieved auth token from sharedpref: " + SharedPrefManager.getAuthToken(this));
		Timber.d("retrieved favorite pokemon id token from sharedpref: " + SharedPrefManager.getFavoritePokemonId(this));
		SharedPrefManager.setFavoritePokemonId(this, 1);
		Timber.d("retrieved favorite pokemon id token from sharedpref: " + SharedPrefManager.getFavoritePokemonId(this));
		SharedPrefManager.setFavoritePokemonId(this, null);
		
		Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", 67);
		Pokemon ivysaur = new Pokemon(2, "Ivysaur", 77);
		Pokemon venusaur = new Pokemon(3, "Venusaur", 107);
		
		PokemonDb.insertLikedPokemon(this, bulbasaur);
		PokemonDb.insertLikedPokemon(this, ivysaur);
		Timber.d("retrieved liked pokemon from sqlite: " + PokemonDb.retrieveLikedPokemon(this));
		Timber.d("insert " + venusaur.name + " into database");
		PokemonDb.insertLikedPokemon(this, venusaur);
		Timber.d("retrieved liked pokemon from sqlite: " + PokemonDb.retrieveLikedPokemon(this));
		PokemonDb.deleteLikedPokemon(this);
	}
	
	public void initialize() {
		navView = findViewById(R.id.nav_view);
		rvDrawer = findViewById(R.id.rv_buttons);
		toolbar = findViewById(R.id.toolbar);
		drawerLayout = findViewById(R.id.drawer_layout);
		toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		rvDrawer = drawerLayout.findViewById(R.id.rv_buttons);
	}

	public void populate() {
		toggle.syncState();
		
		loadSection(model.section);
		
		if(model.drawerOpenedOnStart) drawerLayout.openDrawer(GravityCompat.START);
		
		adapterDrawer = new SidebarRvAdapter(Arrays.asList(MainSection.values()), new SidebarRvAdapter.Callback() {
			@Override
			public void onClick(View view, int index, MainSection section) {
				model.section = section;
				loadSection(model.section);
			}
		});
		rvDrawer.setAdapter(adapterDrawer);
		rvDrawer.setNestedScrollingEnabled(false);
		rvDrawer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
	}
	
	public void setListeners() {
		drawerLayout.addDrawerListener(toggle);
		navView.setNavigationItemSelectedListener(this);
	}
	
	public void loadSection(MainSection section) {
		BaseFragment fragment = null;
		switch (section) {
			case BLUE:
				fragment = new BlueFrag();
				break;
			case RED:
				fragment = new RedFrag();
				break;
			case POKEMON_LIST:
				fragment = new ListFrag();
				break;
		}
		if(fragment!=null) {
			loadFragment(fragment, section);
		}
	}
	
	private void loadFragment(BaseFragment fragment, MainSection section) {
		this.fragment = fragment;
		model.section = section;
		toolbar.setTitle(fragment.getTitle());
		
		invalidateOptionsMenu();
		
		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_main, fragment)
				.commit();
		drawerLayout.closeDrawer(GravityCompat.START);
	}
	
	@Override
	public void onBackPressed() {
		if(drawerLayout!=null) {
			if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
				drawerLayout.closeDrawer(GravityCompat.START);
			} else {
				//We could ask here to the fragment if he wants to consume the event
				super.onBackPressed();
			}
		} else {
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		Boolean result = false;
		switch(item.getItemId()) {
			case android.R.id.home:
				drawerLayout.openDrawer(GravityCompat.START);
				result = true;
				break;
			default:
				if(fragment!=null) {
					result = fragment.onOptionsItemSelected(item);
				}
				break;
		}
		return result;
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		return true;
	}
}