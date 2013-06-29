package de.fu.xml.xread.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.stream.StreamSource;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.activities.sqlAndHelper.ButtonMethods;
import de.fu.xml.xread.main.transformer.Transformer;

@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends AbstractXReadMainActivity {

	private static final String TAG = "WebActivity";

//	ImageButton refreshButton;
	WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webcontent);

//
//		
	}

//	

}
