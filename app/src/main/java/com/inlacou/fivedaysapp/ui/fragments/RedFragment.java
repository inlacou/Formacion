package com.inlacou.fivedaysapp.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.app.DialogCompat;
import androidx.fragment.app.Fragment;

import com.inlacou.fivedaysapp.R;

public class RedFragment extends Fragment {
	
	public ImageButton ibDialog = null;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_red, container, false);
		
		initialize(rootView);
		
		populate();
		
		setListeners();
		
		return rootView;
	}
	
	private void initialize(View rootView) {
		ibDialog = rootView.findViewById(R.id.ib_dialog);
	}
	
	private void populate() {
	}
	
	private void setListeners() {
		ibDialog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
				dialog.setTitle("Red button");
				dialog.setMessage("You clicked the red button!");
				dialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Toast.makeText(getActivity(), "Glad you acknowledged", Toast.LENGTH_SHORT).show();
					}
				});
				dialog.setNeutralButton(R.string.Maybe, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Toast.makeText(getActivity(), "Reality is often disappointing...", Toast.LENGTH_SHORT).show();
					}
				});
				dialog.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						Toast.makeText(getActivity(), "Yes, you did!", Toast.LENGTH_SHORT).show();
					}
				});
				dialog.show();
			}
		});
	}
	
}
