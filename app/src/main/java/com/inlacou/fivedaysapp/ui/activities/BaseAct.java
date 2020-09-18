package com.inlacou.fivedaysapp.ui.activities;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseAct extends AppCompatActivity {
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		Boolean result = false;
		switch(item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
			default:
				//TODO
				break;
		}
		return result;
	}
	
}
