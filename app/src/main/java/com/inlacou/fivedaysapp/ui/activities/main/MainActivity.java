package com.inlacou.fivedaysapp.ui.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;
import com.inlacou.fivedaysapp.R;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
	
	private MainActivityModel model = new MainActivityModel();
	private MainActivityController controller = new MainActivityController(this, model);
	
	public MaterialTextView tvValue = null;
	public MaterialButton btMore = null;
	public MaterialButton btLess = null;
	public MaterialButton btStartNormal = null;
	public MaterialButton btStartResult = null;
	public MaterialButton btStartBrowser = null;
	public MaterialTextView etUrl = null;
	public MaterialCheckBox cbStartActWithData = null;
	public View llStartActWithData = null;
	public MaterialRadioButton cbOpenBrowser = null;
	public MaterialRadioButton cbOpenWebView = null;
	public RadioGroup rgOpenTarget = null;
	public WebView webview = null;
	public AppCompatSpinner spinner = null;
	public AutoCompleteTextView actvFruit = null;
	
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
		btStartNormal = findViewById(R.id.bt_start_activity_normal);
		btStartResult = findViewById(R.id.bt_start_activity_result);
		btStartBrowser = findViewById(R.id.bt_start_browser);
		etUrl = findViewById(R.id.et_url);
		cbStartActWithData = findViewById(R.id.cb_start_act_with_data);
		llStartActWithData = findViewById(R.id.ll_start_act_with_data);
		cbOpenBrowser = findViewById(R.id.cb_open_browser);
		cbOpenWebView = findViewById(R.id.cb_open_webview);
		rgOpenTarget = findViewById(R.id.rg_open_target);
		webview = findViewById(R.id.webview);
		spinner = findViewById(R.id.spinner);
		actvFruit = findViewById(R.id.actv_fruit);
	}
	
	private void populate() {
		updateValue();
		etUrl.setText(model.url);
		cbStartActWithData.setChecked(model.sendDataOnStartActivityForResult);
		actvFruit.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.fruits)));
	}
	
	private void setListeners() {
		llStartActWithData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cbStartActWithData.setChecked(!cbStartActWithData.isChecked());
			}
		});
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
		rgOpenTarget.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				if(radioGroup.getCheckedRadioButtonId()==R.id.cb_open_browser) {
					controller.onCheckBoxOpenBrowserChecked();
				}else{
					controller.onCheckBoxOpenWebViewChecked();
				}
			}
		});
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Timber.d("item selected index: " + i + " | item: " + getResources().getStringArray(R.array.fruits)[i]);
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				Timber.d("item selected: none");
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
	
	public void loadUrlOnWebView(String url) {
		webview.loadUrl(url);
	}
}