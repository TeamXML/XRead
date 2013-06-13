package de.fu.xml.xread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import de.fu.xml.xread.R.id;

public class MainActivity extends Activity {
	
	boolean mainIsOpen = true;
	boolean webcontentIsOpen = false;
	boolean historyIsOpen = false;
	private EditText editText;
	private String uri;
	private static final String TAG = "MainActivity";
	private List<Entry> list = new ArrayList<Entry>();
	private HistoryDataSource dataSource;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        dataSource = new HistoryDataSource(this);
        
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
                Log.i(TAG, "Kein Button mit der ID: " + view.getId() + " vorhanden.");
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
			Toast.makeText(getApplicationContext(), "Textfeld ist leer. Kein Abbruch notwendig!", Toast.LENGTH_SHORT).show();
		}
		else{
			editText.setText("");
			Toast.makeText(getApplicationContext(), "Vorgang abgebrochen.", Toast.LENGTH_SHORT).show();
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
    		Toast.makeText(getApplicationContext(), "Gib eine URL ein ...", Toast.LENGTH_SHORT).show();
    	}
    	else{
    		
    		//Wenn URL invalide
    		if(!urlString.startsWith("http://")){
    			urlString="http://"+urlString;
    			setUri(urlString);
    			
    		}
    		//danach: Feld nicht leer und URL valide
    		setUri(urlString);
			webview();
			String date = getDate();
			String time = getTime();
			//Datenbank-Eintrag
			dataSource.open();
			dataSource.createEntry(date, time, urlString);
			dataSource.close();
    	    
    	}
    	
    }
  
	private String getDate() {
    	//Zeitstempel
    	Calendar cal = Calendar.getInstance();
    	int tag = cal.get(Calendar.DAY_OF_MONTH);
		int monat = cal.get(Calendar.MONTH);
		int jahr = cal.get(Calendar.YEAR);
		String date = tag+"."+monat+"."+jahr;
		return date;
	}
   
    private String getTime(){
    	Calendar cal = Calendar.getInstance();
    	int stunde = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		//int sekunde = cal.get(Calendar.SECOND);
		String time = stunde+":"+minute+" Uhr";
		return time;
    }
	
    private void webview(){
    	setContentView(R.layout.webcontent);
    	webcontentIsOpen = true;
    	mainIsOpen = false;
    	historyIsOpen = false;
    	EditText editText = (EditText)findViewById(id.editText);
    	editText.setText(getUri());
    	
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
        
    //TODO
    /** Check, ob URI g�ltig ist*/
   
	//TODO
	/** �ffnet View, wo alle URIs gelistet sind, die aufgerufen sind (mit TimeStamp)*/ 

	public void history(){
		setContentView(R.layout.history);
		
		mainIsOpen = false;
		historyIsOpen = true;
		webcontentIsOpen = false;
		
		list.clear();
		
		try {
			dataSource.open();
			list = dataSource.getAllEntries();
			dataSource.close();
		} catch (Exception e) {
			Toast.makeText(this, "Fehler beim Auslesen: "+e.toString(), Toast.LENGTH_LONG).show();
		}
		
		ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(MainActivity.this, android.R.layout.simple_list_item_1, list);
		ListView lview = (ListView)findViewById(R.id.listView1);
		lview.setAdapter(adapter);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		//wenn auf zur�ckButton geklickt wird und man in History ist
		if(keyCode == KeyEvent.KEYCODE_BACK && !mainIsOpen && historyIsOpen && !webcontentIsOpen){
			mainIsOpen = true;
			historyIsOpen = false;
			setContentView(R.layout.main2);
			return true;
		}
		
		//wenn auf zur�ckButton geklickt wird und man in WebContent ist
		if(keyCode == KeyEvent.KEYCODE_BACK && !mainIsOpen && webcontentIsOpen && !historyIsOpen){
			mainIsOpen = true;
			webcontentIsOpen = false;
			setContentView(R.layout.main2);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
