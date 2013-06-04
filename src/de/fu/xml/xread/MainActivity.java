package de.fu.xml.xread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import de.fu.xml.xread.R.id;

public class MainActivity extends Activity {
	
	ProgressBar circle;
	boolean mainIsOpen = true;
	Toast toast;
	private EditText editText;
	Uri uri;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
	}
	
	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
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
    			setContentView(R.layout.webcontent);
    			
    			//ProgressWheel erscheint
    			ProgressBar wheel = (ProgressBar)findViewById(id.progressWheel);
    			wheel.setVisibility(ProgressBar.VISIBLE);
    			
    			WebView webview = (WebView)findViewById(id.webView);
    			String url = getUri().toString();
    			webview.loadUrl(url);
    	
    	    }
    	}
    	
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
		setContentView(R.layout.history);
	}
}
