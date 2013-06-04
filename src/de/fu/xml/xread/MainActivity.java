package de.fu.xml.xread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import de.fu.xml.xread.R.id;

public class MainActivity extends Activity {
	
	boolean mainIsOpen = true;
	boolean webcontentIsOpen = false;
	boolean historyIsOpen = false;
	private Toast toast;
	private EditText editText;
	private String uri;
	private static final String TAG = "MyActivity";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

    /** Handler, wenn auf Button geklickt wird - Achtung: in Layout muss Methodenname verankert sein!*/
	public void onButtonClick(View view){
    	switch (view.getId()) {
        	case id.stopButton:{
        		stop();
        		break;
        	}
        	case id.playButton:{
        		play();
        		break;
        	}
        	case id.historyButton:{
        		history();
        		break;
        	}
        	default:{
                Log.e("MainActivity.java", "Kein Button mit der ID: " + view.getId() + " vorhanden.");
                break;
        	}
    	}
           
    }
	
	private void stop(){
		editText = (EditText)findViewById(id.editText);
		String editTextString = editText.getText().toString();
		
		ProgressBar wheel = (ProgressBar)findViewById(id.progressWheel);
		wheel.setVisibility(View.INVISIBLE);
		
		//Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive())
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		
		if(editTextString.length() == 0){
			toast = Toast.makeText(getApplicationContext(), "Textfeld ist leer. Kein Abbruch notwendig!", Toast.LENGTH_SHORT);
	    	toast.show();
		}
		else{
			editText.setText("");
			toast = Toast.makeText(getApplicationContext(), "Vorgang abgebrochen.", Toast.LENGTH_SHORT);
	    	toast.show();
		}
	}
       
    /** Wenn auf Button Play geklickt wird, dann beginnt der Prozess des Parsens */   
    private void play(){
    	
    	//Tastatur ausblenden	
		InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    	    	
    	editText = (EditText)findViewById(id.editText);
    	String urlString = editText.getText().toString();
    	
    	//Wenn URL Feld leer
    	if(urlString.length() <= 0){
    		toast = Toast.makeText(getApplicationContext(), "Gib eine URL ein ...", Toast.LENGTH_SHORT);
    		toast.show();
    	}
    	else{
    		boolean validURL = validURL(urlString);
    		//Wenn URL invalide
    		if(!validURL){
    			toast = Toast.makeText(getApplicationContext(), "Diese URL gibt es nicht ...", Toast.LENGTH_SHORT);
        		toast.show();
    		}
    		//sonst: Feld nicht leer und URL valide
    		else{
    			setUri(urlString);
    			webview();
    	    }
    	}
    	
    }
    
    private void webview(){
    	setContentView(R.layout.webcontent);
    	webcontentIsOpen = true;
    	mainIsOpen = false;
    	
    	WebView webview = (WebView)findViewById(id.webView);
    	final Builder alert = new AlertDialog.Builder(this);

    	
    	final ProgressBar wheel = (ProgressBar)findViewById(id.progressWheel);
    	wheel.setVisibility(View.VISIBLE);
    	
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
		         Log.i(TAG, "Verarbeitung ...");
		         view.loadUrl(url);
		         return true;
			}
		
			@Override
			 public void onPageFinished(WebView view, String url) {
				Log.i(TAG, "Fertig geladen ..." +url);
				wheel.setVisibility(View.INVISIBLE);
			 }
		
			//Fehlerabhandlung
			 public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			     Log.e(TAG, "Error: " + description);
			     Toast.makeText(getBaseContext(), "Hier ist was schief gelaufen!! " + description, Toast.LENGTH_SHORT).show();
			     alert.setTitle("Error");
			     alert.setMessage(description);
			     
			     alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			    	 public void onClick(DialogInterface dialog, int which) {
			    		 return;
			    	 }
			     });
			     alert.show();
			 }
		});
		webview.loadUrl(getUri());
    }

    /** Check, ob URI gültig ist*/
	private boolean validURL(String urlString) {
		boolean result = false;
		URL url;
		URLConnection connection;
		try {
			url = new URL(urlString);
			connection = url.openConnection();
			if(connection.getInputStream() == null) result = false;
			else result = true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
   
	/** öffnet View, wo alle URIs gelistet sind, die aufgerufen sind (mit TimeStamp)*/ 
	public void history(){
		mainIsOpen = false;
		historyIsOpen = true;
		setContentView(R.layout.history);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		//wenn auf zurückButton geklickt wird und man in History ist
		if(keyCode == KeyEvent.KEYCODE_BACK && !mainIsOpen && historyIsOpen){
			mainIsOpen = true;
			historyIsOpen = false;
			setContentView(R.layout.main2);
			return true;
		}
		
		//wenn auf zurückButton geklickt wird und man in WebContent ist
		if(keyCode == KeyEvent.KEYCODE_BACK && !mainIsOpen && webcontentIsOpen){
			mainIsOpen = true;
			webcontentIsOpen = false;
			setContentView(R.layout.main2);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
