package com.inlacou.fivedaysapp.ui.activities.viewpager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.inlacou.fivedaysapp.R;

public class ViewPagerAct extends AppCompatActivity {
	
	private ViewPagerActMdl model = new ViewPagerActMdl();
	private ViewPagerActCtrl controller = new ViewPagerActCtrl(this, model);
	
	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	private ViewPager2 viewPager;
	
	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private FragmentStateAdapter pagerAdapter;
	
	@Override
	public void onBackPressed() {
		if (viewPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
		}
	}
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		
		initialize();
		
		populate();
		
		setListeners();
	}
	
	private void initialize() {
		viewPager = findViewById(R.id.viewpager);
	}
	
	private void populate() {
		pagerAdapter = new ScreenSlidePagerAdapter(this);
		viewPager.setAdapter(pagerAdapter);
	}
	
	private void setListeners() {
	}
	
}
