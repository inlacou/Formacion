package com.inlacou.fivedaysapp.result;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.inlacou.fivedaysapp.R;

public class ResultActivity extends AppCompatActivity {
	
	private ResultActivityModel model = new ResultActivityModel();
	private ResultActivityController controller = new ResultActivityController(this, model);
	
	public Button btEnd = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		handleIntent();
		
		initialize();
		
		populate();
		
		setListeners();
	}
	
	private void handleIntent() {
		model.currentValue = getIntent().getStringExtra("data");
	}
	
	private void initialize() {
		btEnd = findViewById(R.id.bt_end);
	}
	
	private void populate() {}
	
	private void setListeners() {
		btEnd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controller.onButtonEndClick();
			}
		});
	}

	protected void finishOk() {
		setResult(RESULT_OK);
		finish();
	}
	
	protected void finishNok() {
		finish();
	}
}