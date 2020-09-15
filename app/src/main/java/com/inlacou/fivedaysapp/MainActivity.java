package com.inlacou.fivedaysapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	
	private MainActivityModel model = new MainActivityModel();
	private MainActivityController controller = new MainActivityController(this, model);
	
	public TextView tvValue = null;
	public Button btMore = null;
	public Button btLess = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initialize();
		
		populate();
		
		setListeners();
	}
	
	private void initialize() {
		tvValue = findViewById(R.id.tv_value);
		btMore = findViewById(R.id.bt_more);
		btLess = findViewById(R.id.bt_less);
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
	}
	
	public void updateValue() {
		tvValue.setText("Value: " + model.currentValue);
	}
}