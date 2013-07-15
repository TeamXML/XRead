package de.fu.xml.xread.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import de.fu.xml.xread.R;
import de.fu.xml.xread.helper.EnablingTextWatcher;
import de.fu.xml.xread.helper.WebHelper;

@SuppressLint("SetJavaScriptEnabled")
public class SettingsActivity extends XReadActivity {

	private static final String TAG = "SettingsActivity";

	Button _saveButton;
	EditText _serverAdressText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
		super.onCreate(savedInstanceState);

		_saveButton = (Button) findViewById(R.id.saveSettingsButton);
		_serverAdressText = (EditText) findViewById(R.id.serverAdressEditText);

		_serverAdressText.addTextChangedListener(new EnablingTextWatcher(
				_saveButton));

		final SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		
		String serverAdressString = preferences.getString("serverAdress",
						"10.0.2.2:8080");
		_serverAdressText.setText(serverAdressString);
		
		_saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences("serverAdress", MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				final String newServerAdress = _serverAdressText.getText().toString();
				editor.putString("serverAdress", newServerAdress);
				editor.commit();
				
				WebHelper.SetServerAdress(newServerAdress);
			}
		});
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.settings;
	}

	@Override
	protected Context GetContext() {
		return this;
	};
}
