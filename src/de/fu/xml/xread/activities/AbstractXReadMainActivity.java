package de.fu.xml.xread.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import de.fu.xml.xread.R;
import de.fu.xml.xread.helper.DateHelper;
import de.fu.xml.xread.helper.HistoryDataSource;

public abstract class AbstractXReadMainActivity extends XReadActivity {

	HistoryDataSource dataSource;
	
	protected ImageButton stopButton;
	protected ImageButton playButton;
	protected ImageButton historyButton;
	protected EditText editText;
	protected ProgressBar progressWheel;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataSource = new HistoryDataSource(getApplicationContext());
	}

	/**
	 * Handler, wenn auf Button geklickt wird - Achtung: in Layout muss
	 * Methodenname verankert sein!
	 */
	protected void onButtonClick(View view) {

		hideKeyboard();

		switch (view.getId()) {
			case R.id.historyButtonMain: {
					history();
					break;
				}
		}
		
		switch (view.getId()) {
		case R.id.settingsButton: {
				settings();
				break;
			}
	}

	}
	
	private void settings() {
		startIntent(SettingsActivity.class);
	}

	private void history() {
		startIntent(HistoryActivity.class);
	}

	
	protected void createHistoryEntry(String urlString) {
		String date = DateHelper.getDate();
		String time = DateHelper.getTime();

		// Datenbank-Eintrag
		dataSource.open();
		dataSource.createEntry(date, time, urlString);
		dataSource.close();
	}
}
