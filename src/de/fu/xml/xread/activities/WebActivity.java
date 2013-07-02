package de.fu.xml.xread.activities;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.helper.WebHelper;
import de.fu.xml.xread.main.Transformer;

@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends AbstractXReadMainActivity {

	private static final String TAG = "WebActivity";

	private float _webview_downX;
	
	WebView webview;
	ImageButton refreshButton;
	Transformer transformer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
		super.onCreate(savedInstanceState);

		transformer = new Transformer(getApplicationContext());

		refreshButton = (ImageButton) findViewById(id.refreshButtonWeb);
		stopButton = (ImageButton) findViewById(id.stopButtonWeb);
		playButton = (ImageButton) findViewById(id.playButtonWeb);
		historyButton = (ImageButton) findViewById(id.historyButtonWeb);
		progressWheel = (ProgressBar) findViewById(id.progressWheelWeb);
		editText = (EditText) findViewById(R.id.editTextWeb);

		webview = (WebView) findViewById(id.webView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setDomStorageEnabled(true);
		webview.setWebChromeClient(new WebChromeClient(){
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				Log.i(TAG, url+ ": " +message);
				return super.onJsAlert(view, url, message, result);
			}
		});
		webview.setWebViewClient(new WebViewClient() {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				WebHelper.setUri(url);
				createHistoryEntry(WebHelper.getUri());
				loadWebContent();
				return true;
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				stopButton.setVisibility(View.VISIBLE);
				refreshButton.setVisibility(View.INVISIBLE);
				progressWheel.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				stopButton.setVisibility(View.INVISIBLE);
				refreshButton.setVisibility(View.VISIBLE);
				progressWheel.setVisibility(View.INVISIBLE);
				webview.postInvalidateDelayed(500);
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				handleError(description, TAG);
			}
			
		});
		
		 webview.setHorizontalScrollBarEnabled(false);
		 webview.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {

	                switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN: {
	                    // save the x
	                    _webview_downX = event.getX();
	                }
	                    break;

	                case MotionEvent.ACTION_MOVE:
	                case MotionEvent.ACTION_CANCEL:
	                case MotionEvent.ACTION_UP: {
	                    // set x so that it doesn't move
	                    event.setLocation(_webview_downX, event.getY());
	                }
	                    break;

	                }

	                return false;
	            }
	        });

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
		progressWheel.setVisibility(View.INVISIBLE);
		refreshButton.setVisibility(View.VISIBLE);
		webview.stopLoading();
	}

	public void loadWebContent() {
		
		String data;
		try {
			String uri = WebHelper.getUri();
			data = new LoadURLTask().execute(uri).get();
			webview.loadData(data, "text/html", "ISO-8859-1");
		} catch (Exception e) {
			handleError("Fehler beim Laden der Webdaten", e, TAG);
		}
	}

	private class LoadURLTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				return transformer.transformData(params[0]);
			} catch (IOException e) {
				handleError("Fehler beim Transformieren der Daten", e, TAG);
				return null;
			}
		}

	}
}
