package com.inlacou.fivedaysapp.ui.activities.viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.inlacou.fivedaysapp.ui.fragments.blue.BlueFrag;
import com.inlacou.fivedaysapp.ui.fragments.red.RedFrag;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
class ScreenSlidePagerAdapter extends FragmentStateAdapter {
	/**
	 * The number of pages to show.
	 */
	private static final int NUM_PAGES = 2;
	
	public ScreenSlidePagerAdapter(FragmentActivity fa) {
		super(fa);
	}
	
	@Override
	public Fragment createFragment(int position) {
		if(position%2==0) return new RedFrag();
		else return new BlueFrag();
	}
	
	@Override
	public int getItemCount() {
		return NUM_PAGES;
	}
}
