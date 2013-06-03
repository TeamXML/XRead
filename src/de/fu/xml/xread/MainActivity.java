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
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;
import de.fu.xml.xread.R.id;

public class MainActivity extends Activity {
	
	ProgressBar circle;
	boolean mainIsOpen = true;
	Toast toast;
	private AutoCompleteTextView textField;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Aufgabe: Autovervollständigung für textfeld mit den Inhalten, die in einem Array von Histories stehen

    }

    /** Handler, wenn auf Button geklickt wird - Achtung: in Layout muss Methodenname verankert sein!*/
	public void onButtonClick(View view){
    	switch (view.getId()) {
        	case id.cancelButton:{
        		cancel();
        		break;
        	}
        	case id.startButton:{
        		play();
        		break;
        	}
        	case id.exitButton:{
        		exit();
        		break;
        	}
        	case id.infoButton:{
        		info();
        		break;
        	}
        	case id.logoHeader:{
        		info();
        		break;
        	}
        	default:{
                Log.e("MainActivity.java", "Kein Button mit der ID: " + view.getId() + " vorhanden.");
                break;
        	}
    	}
           
    }
    
    /** Wenn auf Button Cancel geklickt wird, dann soll der aktuelle Prozess abgebrochen werden */
    public void cancel(){

		//Tastatur ausblenden	
		InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    	
    	textField = (AutoCompleteTextView)findViewById(id.editTextURI);
    	textField.setText("");
    	toast = Toast.makeText(getApplicationContext(), "Vorgang abgebrochen!", Toast.LENGTH_SHORT);
    	toast.show();
    	
    	
    }
    
    /** Wenn auf Button Play geklickt wird, dann beginnt der Prozess des Parsens */   
    public void play(){
    	//Tastatur ausblenden	
		InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    	    	
    	textField = (AutoCompleteTextView)findViewById(id.editTextURI);
    	String urlString = textField.getText().toString();
    	
    	//Wenn URL Feld leer
    	if(urlString.length() <= 0){
    		toast = Toast.makeText(getApplicationContext(), "Gib eine URI ein ...", Toast.LENGTH_SHORT);
    		toast.show();
    	}
    	else{
    		boolean validURL = validURL(urlString);
    		//Wenn URL invalide
    		if(!validURL){
    			toast = Toast.makeText(getApplicationContext(), "URI ist nicht valide ...", Toast.LENGTH_SHORT);
        		toast.show();
    		}
    		//sonst: Feld nicht leer und URL valide
    		else{
    			//ProgressWheel erscheint
    			ProgressBar circle = (ProgressBar)findViewById(id.progressCircle);
    			circle.setVisibility(ProgressBar.VISIBLE);
        		//Tastatur ausblenden	
    			toast = Toast.makeText(getApplicationContext(), "Starte Download ...", Toast.LENGTH_SHORT);
    			toast.show();
    	    	

    	    	//TODO Datenbank-Eintrag der URI ...
    	
    	    }
    	}
    	
    }

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

	public void exit(){
    	Builder build = new AlertDialog.Builder(this);
		build.setMessage("Programm wird geschlossen!");
		build.setCancelable(true);
		
		build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.finish();
			}
		});
		
		build.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "Programm wird fortgesetzt", Toast.LENGTH_LONG).show();	
			}
		});
		
		AlertDialog dialog = build.create();
		dialog.show();
    }
    
    public void info(){
    	setContentView(R.layout.info);
    	mainIsOpen = false;
    }
    
    /** Wenn auf HardDeviceButton "Zurück" gelickt wird im info.xml, dann wieder main.xml öffnen */
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		if(keyCode == KeyEvent.KEYCODE_BACK && !mainIsOpen){
			setContentView(R.layout.main);
			mainIsOpen = true;
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
      
}
