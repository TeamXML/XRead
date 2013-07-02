package de.fu.xml.xread.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.helper.WebHelper;

public class MainActivity extends AbstractXReadMainActivity {

	ImageButton stopButton;
	ImageButton playButton;
	ImageButton historyButton;
	ImageButton twitterButton;
	ImageButton globeButton;
	ImageButton stackoverflowButton;
	ImageButton dbpediaButton;
	EditText editText;
	ProgressBar progressWheel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		stopButton = (ImageButton) findViewById(id.stopButtonMain);
		playButton = (ImageButton) findViewById(id.playButtonMain);
		historyButton = (ImageButton) findViewById(id.historyButtonMain);
		twitterButton = (ImageButton) findViewById(id.twitterButton);
		globeButton = (ImageButton) findViewById(id.geoButton);
		stackoverflowButton = (ImageButton) findViewById(id.stackoverflowButton);
		dbpediaButton = (ImageButton) findViewById(id.mediaButton);
		progressWheel = (ProgressBar) findViewById(id.progressWheelMain);
		
		editText = (EditText) findViewById(id.editTextMain);
		editText.setSelectAllOnFocus(true);
		editText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				editText.setText("");
			}
		});
	}
	
	@Override
	protected int getLayoutResourceId() {
		return R.layout.main;
	}
	
	@Override
	protected Context GetContext() {		
		return this;
	};
	
	@Override
	public void onButtonClick(View view) {

		super.onButtonClick(view);

		switch (view.getId()) {
			case id.playButtonMain: {
				playMain();
				break;
			}
			case id.twitterButton: {
				twitter();
				break;
			}
			case id.geoButton: {
				geo();
				break;
			}
			case id.stackoverflowButton: {
				stackoverflow();
				break;
			}
			case id.mediaButton: {
				dbpedia();
				break;
			}
		}

	}
	
	private void playMain() {
		hideKeyboard();

		// Wenn URL Feld leer
		if (editText.length() <= 0) {
			showToast("Geben Sie eine URL ein!");
		} else {
			String urlString = editText.getText().toString();
			WebHelper.setUri(urlString);
			createHistoryEntry(WebHelper.getUri());

			webview();
		}
	}
	
	
	public void webview() {
		startIntent(WebActivity.class);
	}
	
	private void geo() {
		startIntent(GeoActivity.class);
	}
	
	private void twitter() {
		startIntent(TwitterActivity.class);
	}	

	private void dbpedia() {
		startIntent(DBPediaActivity.class);
	}

	private void stackoverflow() {
		startIntent(StackoverflowActivity.class);
	}
}
