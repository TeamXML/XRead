package de.fu.xml.xread.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.fu.xml.xread.R;
import de.fu.xml.xread.helper.ButtonMethods;
import de.fu.xml.xread.helper.HistoryDataSource;

public abstract class AbstractXReadMainActivity extends XReadActivity {

	protected static final String HTTP = "http://";
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

	}
	
	protected void stop(TextView editText, View progressWheel) {
		if (editText.length() <= 0)
			showToast(getString(R.string.no_need_to_cancel));
		else {
			ButtonMethods.setUri("");
			editText.setText(ButtonMethods.getUri());
			showToast(getString(R.string.cancelled));
			progressWheel.setVisibility(View.INVISIBLE);
		}
	}

	private void history() {
		startIntent(HistoryActivity.class);
	}

	
	protected void createHistoryEntry(String urlString) {
		String date = ButtonMethods.getDate();
		String time = ButtonMethods.getTime();

		// Datenbank-Eintrag
		dataSource.open();
		dataSource.createEntry(date, time, urlString);
		dataSource.close();
	}
}
