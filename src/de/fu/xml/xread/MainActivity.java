package de.fu.xml.xread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openrdf.repository.RepositoryException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.main.RDFTranslator;

public class MainActivity extends Activity {
	
	boolean mainIsOpen = true;
	boolean webcontentIsOpen = false;
	boolean historyIsOpen = false;
	private String uri;
	private static final String TAG = "MainActivity";
	private List<Entry> list = new ArrayList<Entry>();
	private HistoryDataSource dataSource;
	
	ImageButton stopButton;
	ImageButton playButton;
	ImageButton historyButton;
	EditText editText;
	WebView webview;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dataSource = new HistoryDataSource(this);
        
        stopButton = (ImageButton)findViewById(id.stopButton);
    	playButton = (ImageButton)findViewById(id.playButton);
    	historyButton = (ImageButton)findViewById(id.historyButton);
    	editText = (EditText)findViewById(id.editText);
    	webview = (WebView)findViewById(id.webView);    	    
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
	
	/**	Wenn auf Button Stop geklickt wird, dann wird der Vorgang des Ladens abgebochen. */
	private void stop(){
		EditText editText = (EditText)findViewById(id.editText);
		ProgressBar progressWheel = (ProgressBar)findViewById(id.progressWheel);
		
		if(editText.length() <= 0) 
    		Toast.makeText(getApplicationContext(), "Textfeld ist leer. Kein Abbruch notwendig!", Toast.LENGTH_SHORT).show();
		else{
			setUri("");
			editText.setText(getUri());
			Toast.makeText(getApplicationContext(), "Vorgang abgebrochen.", Toast.LENGTH_SHORT).show();
			progressWheel.setVisibility(View.INVISIBLE);
			setContentView(R.layout.main);
		}
	}
       
    /** Wenn auf Button Play geklickt wird, dann beginnt der Prozess des Parsens */   
    private void play(){
    	
    	//TODO: nur zu testzwecken! Gleich wieder entfernen!
    	AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				RDFTranslator mainTranslator;
				try {
					mainTranslator = RDFTranslator.getInstance(getApplicationContext());
					Log.i(TAG, "MainTranslator: ");
					String translateResource = mainTranslator.translateResource("http://linkedgeodata.org/triplify/node264695865");
					Log.i(TAG, translateResource);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e(TAG, "Exception: ", e);
				}
				return null;
			}
		};
		task.execute("");    	    	    	    
    	
    	editText = (EditText)findViewById(id.editText);
    	
//    	//Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	if(imm.isActive())
    		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    		
    	//Wenn URL Feld leer
    	if(editText.length() <= 0)
    		Toast.makeText(getApplicationContext(), "Gib eine URL ein ...", Toast.LENGTH_SHORT).show();
    	else{
    		String urlString = editText.getText().toString();
    		//Wenn URL invalide
    		if(!urlString.startsWith("http://"))
    			urlString="http://"+urlString;
    	
    		//danach: Feld nicht leer und URL valide
    		setUri(urlString);
			
    		editText.setText(getUri());
			
			String date = getDate();
			String time = getTime();
			//Datenbank-Eintrag
			dataSource.open();
			dataSource.createEntry(date, time, urlString);
			dataSource.close();
			
			webview();
			
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
		String time = stunde+":"+minute+" Uhr";
		return time;
    }
	
    private void webview(){
    	setContentView(R.layout.webcontent);
    	
    	//EditText editText = (EditText)findViewById(id.editText);
		editText.setText(getUri());
    	
    	webcontentIsOpen = true;
    	mainIsOpen = false;
    	historyIsOpen = false;
    	
    	final Builder alert = new AlertDialog.Builder(this);
    	
    	final ProgressBar progressWheel = (ProgressBar)findViewById(id.progressWheel);
    	
    	
    	WebView webview = (WebView)findViewById(id.webView);
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				stopButton.setImageResource(R.drawable.android_stop);
				progressWheel.setVisibility(View.VISIBLE);
				Log.i(TAG, "Verarbeitung ...");
		        view.loadUrl(url);
		        return true;
			}
		
			@Override
			 public void onPageFinished(WebView view, String url) {
				stopButton.setImageResource(R.drawable.android_refresh);
				Log.i(TAG, "Fertig geladen ..." +url);
				progressWheel.setVisibility(View.INVISIBLE);
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
    /** Check, ob URI gueltig ist*/
   
	//TODO
	/** Oeffnet View, wo alle URIs gelistet sind, die aufgerufen sind (mit TimeStamp)*/ 
	public void history(){
		setContentView(R.layout.history);
		
		mainIsOpen = false;
		historyIsOpen = true;
		webcontentIsOpen = false;
		
		try {
			dataSource.open();
			list = dataSource.getAllEntries();
			dataSource.close();
		} catch (Exception e) {
			Toast.makeText(this, "Fehler beim Auslesen: "+e.toString(), Toast.LENGTH_LONG).show();
		}
		
		ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this, android.R.layout.simple_list_item_1, list);
		final ListView lview = (ListView)findViewById(R.id.listView1);
		lview.setAdapter(adapter);
		lview.setClickable(true);
		lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String itemString = lview.getItemAtPosition(arg2).toString();
				
				String[] list = itemString.split(" ");
				String link = list[1];
				link = link.replace("besucht", "");
				link = link.replace("\n", "");
				
				setUri(link);
				String date = getDate();
				String time = getTime();
				//Datenbank-Eintrag
				dataSource.open();
				dataSource.createEntry(date, time, getUri());
				dataSource.close();
				
				webview();
			}
		
		});
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		//wenn auf zurueckButton geklickt wird und man in History ist
		if(keyCode == KeyEvent.KEYCODE_BACK && !mainIsOpen && historyIsOpen && !webcontentIsOpen){
			mainIsOpen = true;
			historyIsOpen = false;
			setContentView(R.layout.main);
			return true;
		}
		
		//wenn auf zurueckButton geklickt wird und man in WebContent ist
		if(keyCode == KeyEvent.KEYCODE_BACK && !mainIsOpen && webcontentIsOpen && !historyIsOpen){
			mainIsOpen = true;
			webcontentIsOpen = false;
			setContentView(R.layout.main);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
