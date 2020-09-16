package com.inlacou.fivedaysapp.ui.fragments.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.inlacou.fivedaysapp.R;

public class DialogFragment extends androidx.fragment.app.DialogFragment {
	
	private ImageButton ib = null;
	private Callback callback = null;
	
	public DialogFragment() {}
	public DialogFragment(Callback callback) { this.callback = callback; }
	
	public interface Callback {
		boolean onButtonClick();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_blue, container, false);
		
		initialize(rootView);
		
		populate();
		
		setListeners();
		
		return rootView;
	}
	
	private void initialize(View rootView) {
		ib = rootView.findViewById(R.id.ib);
	}
	
	private void populate() {}
	
	private void setListeners() {
		ib.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(callback==null || !callback.onButtonClick()) {
					Toast.makeText(getActivity(), "button click not handled", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
}
