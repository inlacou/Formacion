package com.inlacou.fivedaysapp.ui.activities.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.adapters.SidebarRvAdapter;
import com.inlacou.fivedaysapp.business.MainSection;
import com.inlacou.fivedaysapp.services.ControlService;
import com.inlacou.fivedaysapp.ui.fragments.BaseFragment;
import com.inlacou.fivedaysapp.ui.fragments.blue.BlueFrag;
import com.inlacou.fivedaysapp.ui.fragments.pokemon.list.all.AllPokemonListFrag;
import com.inlacou.fivedaysapp.ui.fragments.pokemon.list.liked.LikedPokemonListFrag;
import com.inlacou.fivedaysapp.ui.fragments.red.RedFrag;

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

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	
	@BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
	@BindView(R.id.toolbar) Toolbar toolbar;
	@BindView(R.id.rv_buttons) RecyclerView rvDrawer;
	@BindView(R.id.nav_view) NavigationView navView;
	
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
		
		ControlService.start(this);
	}
	
	public void initialize() {
		ButterKnife.bind(this);
		toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		rvDrawer = drawerLayout.findViewById(R.id.rv_buttons);
	}

	public void populate() {
		toggle.syncState();
		
		loadSection(model.section);
		
		if(model.drawerOpenedOnStart) drawerLayout.openDrawer(GravityCompat.START);
		
		adapterDrawer = new SidebarRvAdapter(Arrays.asList(MainSection.values()), (view, index, section) -> {
			model.section = section;
			loadSection(model.section);
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
				fragment = new AllPokemonListFrag();
				break;
			case LIKED_POKEMON_LIST:
				fragment = new LikedPokemonListFrag();
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