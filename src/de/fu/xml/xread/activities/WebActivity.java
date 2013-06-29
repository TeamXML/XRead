package de.fu.xml.xread.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.stream.StreamSource;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.helper.ButtonMethods;
import de.fu.xml.xread.main.transformer.Transformer;

@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends AbstractXReadMainActivity {

	private static final String TAG = "WebActivity";

	WebView webview;
	ImageButton refreshButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
		super.onCreate(savedInstanceState);
		
		refreshButton = (ImageButton) findViewById(id.refreshButtonWeb);
		stopButton = (ImageButton) findViewById(id.stopButtonWeb);
		playButton = (ImageButton) findViewById(id.playButtonWeb);
		historyButton = (ImageButton) findViewById(id.historyButtonWeb);
		progressWheel = (ProgressBar) findViewById(id.progressWheelWeb);
		
		webview = (WebView) findViewById(id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		
		webview.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				stopButton.setVisibility(View.VISIBLE);
				refreshButton.setVisibility(View.INVISIBLE);
				progressWheel.setVisibility(View.VISIBLE);
				createHistoryEntry(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				stopButton.setVisibility(View.INVISIBLE);
				refreshButton.setVisibility(View.VISIBLE);
				progressWheel.setVisibility(View.INVISIBLE);
			}

			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				handleError(description, TAG);
			}
		});

		ButtonMethods.setMainIsOpen(false);
		ButtonMethods.setWebIsOpen(true);

		loadWebContent();
		
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.webcontent;
	}
	
	@Override
	protected Context GetContext() {		
		return this;
	};
	
	/**
	 * Handler, wenn auf Button geklickt wird - Achtung: in Layout muss
	 * Methodenname verankert sein!
	 */
	public void onButtonClick(View view) {

		super.onButtonClick(view);
		
		switch (view.getId()) {
			case id.stopButtonWeb: {
				stopWeb();
				break;
			}
			case id.playButtonWeb: {
				playWeb();
				break;
			}
			case id.refreshButtonWeb: {
				playWeb();
				break;
			}
		}

	}

	private void playWeb() {
		hideKeyboard();
		loadWebContent();
	}

	/**
	 * Stops loading and returns to MainActivity
	 */
	private void stopWeb() {
		stop(editText, progressWheel);
		startIntent(MainActivity.class);
	}

	public void loadWebContent() {
		
		String data;
		try {
			data = new LoadURLTask().execute(ButtonMethods.getUri()).get();
			webview.loadData(data, "text/html", "UTF-8");
		} catch (Exception e) {
			handleError(getString(R.string.error_loading_web_data), e, TAG);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startIntent(MainActivity.class);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private class LoadURLTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			URL url;
			try {
				url = new URL(params[0]);
				URLConnection urlConnection = url.openConnection();
				InputStream result = urlConnection.getInputStream();

				Transformer transformer = new Transformer(getApplicationContext());
				return transformer.transformGeoData(new StreamSource(result));
			} catch (IOException e) {
				handleError(getString(R.string.error_transforming_data),e, TAG);
				return null;
			}
		}

	}
}
