package com.inlacou.fivedaysapp.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inlacou.fivedaysapp.R;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
	
	private MainActivityModel model = new MainActivityModel();
	private MainActivityController controller = new MainActivityController(this, model);
	
	public TextView tvValue = null;
	public Button btMore = null;
	public Button btLess = null;
	public Button btStartNormal = null;
	public Button btStartResult = null;
	public Button btStartResultWithData = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Timber.plant(new Timber.DebugTree()); //TODO move to Application
		
		initialize();
		
		populate();
		
		setListeners();
	}
	
	private void initialize() {
		tvValue = findViewById(R.id.tv_value);
		btMore = findViewById(R.id.bt_more);
		btLess = findViewById(R.id.bt_less);
		btStartNormal = findViewById(R.id.bt_start_activity_normal);
		btStartResult = findViewById(R.id.bt_start_activity_result);
		btStartResultWithData = findViewById(R.id.bt_start_activity_result_with_data);
	}
	
	private void populate() {
		updateValue();
	}
	
	private void setListeners() {
		btMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controller.onButtonMoreClick();
			}
		});
		btLess.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controller.onButtonLessClick();
			}
		});
		btStartNormal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controller.onButtonStartNormalClick();
			}
		});
		btStartResult.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controller.onButtonStartResultClick();
			}
		});
		btStartResultWithData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controller.onButtonStartResultWithDataClick();
			}
		});
	}
	
	public void updateValue() {
		tvValue.setText("Value: " + model.currentValue);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		controller.onActivityResult(requestCode, resultCode, data);
	}
	
	public void showToast(String s) {
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Timber.d("onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Timber.d("onResume");
	}
	
	@Override
	protected void onPause() {
		Timber.d("onPause");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		Timber.d("onStop");
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Timber.d("onRestart");
	}
	
	@Override
	protected void onDestroy() {
		Timber.d("onDestroy");
		super.onDestroy();
	}
	
}