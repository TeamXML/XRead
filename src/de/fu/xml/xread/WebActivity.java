package de.fu.xml.xread;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import javax.xml.transform.stream.StreamSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.main.transformer.Transformer;

public class WebActivity extends Activity {

	private static final String TAG = "WebActivity";

	private HistoryDataSource dataSource;

	ImageButton stopButton;
	ImageButton playButton;
	ImageButton historyButton;
	ImageButton refreshButton;
	EditText editText;
	WebView webview;
	ProgressBar progressWheel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webcontent);

		dataSource = new HistoryDataSource(this);
		stopButton = (ImageButton) findViewById(id.stopButtonWeb);
		playButton = (ImageButton) findViewById(id.playButtonWeb);
		historyButton = (ImageButton) findViewById(id.historyButtonWeb);
		refreshButton = (ImageButton) findViewById(id.refreshButtonWeb);

		editText = (EditText) findViewById(id.editTextWeb);
		editText.setSelectAllOnFocus(true);
		editText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				editText.setText("");
			}
		});

		// Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isAcceptingText())
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		webview = (WebView) findViewById(id.webView);
		progressWheel = (ProgressBar) findViewById(id.progressWheelWeb);

		ButtonMethods.setMainIsOpen(false);
		ButtonMethods.setWebIsOpen(true);

		webview();
	}

	/**
	 * Handler, wenn auf Button geklickt wird - Achtung: in Layout muss
	 * Methodenname verankert sein!
	 */
	public void onButtonClick(View view) {
		// Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isAcceptingText())
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		switch (view.getId()) {
		case id.stopButtonWeb: {
			stopWeb();
			break;
		}
		case id.playButtonWeb: {
			playWeb();
			break;
		}
		case id.historyButtonWeb: {
			history();
			break;
		}
		case id.refreshButtonWeb: {
			refresh();
			break;
		}
		default: {
			Toast.makeText(this, "Huh?! Dieser Button existiert nicht!",
					Toast.LENGTH_LONG).show();
			break;
		}
		}

	}

	public void refresh() {
		// Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isAcceptingText())
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		webview();
	}

	private void history() {
		Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
		startActivity(i);
	}

	/**
	 * Wenn auf Button Stop geklickt wird in Web Ansicht, dann wird der Vorgang
	 * des Ladens abgebochen.
	 */
	public void stopWeb() {
		// Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isAcceptingText())
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		if (editText.length() <= 0)
			Toast.makeText(getApplicationContext(),
					"Textfeld ist leer. Kein Abbruch notwendig!",
					Toast.LENGTH_SHORT).show();
		else {
			ButtonMethods.setUri("");
			editText.setText(ButtonMethods.getUri());
			Toast.makeText(getApplicationContext(), "Vorgang abgebrochen ...",
					Toast.LENGTH_SHORT).show();
			progressWheel.setVisibility(View.INVISIBLE);

			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);

		}
	}

	/** Wenn auf Button Play geklickt wird, dann beginnt der Prozess des Parsens */
	public void playWeb() {

		// Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isAcceptingText())
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);

		// Wenn URL Feld leer
		if (editText.length() <= 0)
			Toast.makeText(getApplicationContext(), "Gib eine URL ein ...",
					Toast.LENGTH_SHORT).show();
		else {

			String urlString = editText.getText().toString();
			// Wenn URL invalide
			if (!urlString.startsWith("http://"))
				urlString = "http://" + urlString;

			// danach: Feld nicht leer und URL valide
			ButtonMethods.setUri(urlString);

			editText.setText(ButtonMethods.getUri());

			String date = ButtonMethods.getDate();
			String time = ButtonMethods.getTime();
			// Datenbank-Eintrag
			dataSource.open();
			dataSource.createEntry(date, time, urlString);
			dataSource.close();

			webview();

		}

	}

	public void webview() {

		editText.setText(ButtonMethods.getUri());

		final Builder alert = new AlertDialog.Builder(this);

		webview.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// Toast.makeText(getApplicationContext(), "Laedt ...",
				// Toast.LENGTH_SHORT).show();
				stopButton.setVisibility(View.VISIBLE);
				refreshButton.setVisibility(View.INVISIBLE);
				progressWheel.setVisibility(View.VISIBLE);
				ButtonMethods.setUri(url);
				editText.setText(ButtonMethods.getUri());
				// view.loadUrl(url);

				// Datenbank-Eintrag
				String date = ButtonMethods.getDate();
				String time = ButtonMethods.getTime();
				dataSource.open();
				dataSource.createEntry(date, time, url);
				dataSource.close();
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				stopButton.setVisibility(View.INVISIBLE);
				refreshButton.setVisibility(View.VISIBLE);
				progressWheel.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(),
						"Seite fertig geladen ...", Toast.LENGTH_SHORT).show();
			}

			// Fehlerabhandlung
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(getBaseContext(),
						"Hier ist was schief gelaufen!! " + description,
						Toast.LENGTH_SHORT).show();
				alert.setTitle("Error");
				alert.setMessage(description);

				alert.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				alert.show();
			}
		});
		
		// TODO: Hier scheint die stelle zu sein, an der wir unsere view
		// einhaengen

		AsyncTask<String, Void, String> loadHTMLTask = new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
					URL url;
					try {
						url = new URL(params[0]);
						URLConnection urlConnection = url.openConnection();
						InputStream result = urlConnection.getInputStream();
						
						Transformer transformer = new Transformer(
								getApplicationContext());							
						return transformer.transformGeoData(new StreamSource(result));
					} catch (IOException e) {
						return null;
					}
			}
			
		};
		
		String data;
		try {
			data = loadHTMLTask.execute(ButtonMethods.getUri()).get();
			Log.i(TAG, data);
			webview.loadData(data,"text/html", "UTF-8");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//webview.loadUrl(ButtonMethods.getUri());

	}

	public void myHTMLintoWebview(String html) {
		webview.loadData(html, "text/html", "UTF-8");
		editText.setText("");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// wenn auf zurueckButton geklickt wird und man in WebContent ist
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
