package com.inlacou.fivedaysapp.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
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
	public Button btStartBrowser = null;
	public EditText etUrl = null;
	public CheckBox cbStartActWithData = null;
	
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
		btStartBrowser = findViewById(R.id.bt_start_browser);
		etUrl = findViewById(R.id.et_url);
		cbStartActWithData = findViewById(R.id.cb_start_act_with_data);
	}
	
	private void populate() {
		updateValue();
		etUrl.setText(model.url);
		cbStartActWithData.setChecked(model.sendDataOnStartActivityForResult);
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
		btStartBrowser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				controller.onButtonOpenBrowserClick();
			}
		});
		etUrl.addTextChangedListener(new TextWatcher() {
			@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
			@Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
			@Override public void afterTextChanged(Editable editable) { model.url = editable.toString(); }
		});
		cbStartActWithData.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				model.sendDataOnStartActivityForResult = b;
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