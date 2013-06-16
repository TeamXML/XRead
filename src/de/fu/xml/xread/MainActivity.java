package de.fu.xml.xread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import de.fu.xml.xread.R.id;

public class MainActivity extends Activity {
	
	HistoryDataSource dataSource;
	
	ImageButton stopButton;
	ImageButton playButton;
	ImageButton historyButton;
	EditText editText;
	ProgressBar progressWheel;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("MainActivity");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        stopButton = (ImageButton)findViewById(id.stopButtonMain);
    	playButton = (ImageButton)findViewById(id.playButtonMain);
    	historyButton = (ImageButton)findViewById(id.historyButtonMain);
    	editText = (EditText)findViewById(id.editTextMain);
    	progressWheel = (ProgressBar)findViewById(id.progressWheelMain);

    	dataSource = new HistoryDataSource(this);
    	
    	//Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	if(imm.isAcceptingText())
    		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    	
		ButtonMethods.setMainIsOpen(true);
    	ButtonMethods.setWebIsOpen(false);
	}
		
    /** Handler, wenn auf Button geklickt wird - Achtung: in Layout muss Methodenname verankert sein!*/
	public void onButtonClick(View view){
    	
		switch (view.getId()) {
        	case id.stopButtonMain:{
        		stopMain();
        		break;
        	}
        	case id.playButtonMain:{
        		playMain();
        		break;
        	}
        	case id.historyButtonMain:{
        		history();
        		break;
        	}
        	default:{
                Toast.makeText(this, "Huh?! Dieser Button existiert nicht!", Toast.LENGTH_LONG).show();
                break;
        	}
    	}
           
    }
	
	/**	Wenn auf Button Stop geklickt wird in Main Ansicht, dann wird der Vorgang des Ladens abgebochen. */
	public void stopMain(){
		//Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	if(imm.isAcceptingText())
    		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    	
		if(editText.length() <= 0) 
    		Toast.makeText(getApplicationContext(), "Textfeld ist leer. Kein Abbruch notwendig!", Toast.LENGTH_SHORT).show();
		else{
			ButtonMethods.setUri("");
			editText.setText(ButtonMethods.getUri());
			Toast.makeText(getApplicationContext(), "Vorgang abgebrochen.", Toast.LENGTH_SHORT).show();
			progressWheel.setVisibility(View.INVISIBLE);
		}
	}
	
	 /** Wenn auf Button Play geklickt wird, dann beginnt der Prozess des Parsens */   
    public void playMain(){
    	//Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	if(imm.isAcceptingText())
    		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    	
    	//Wenn URL Feld leer
    	if(editText.length() <= 0)
    		Toast.makeText(getApplicationContext(), "Gib eine URL ein ...", Toast.LENGTH_SHORT).show();
    	else{
    		String urlString = editText.getText().toString();
    		//Wenn URL invalide
    		if(!urlString.startsWith("http://"))
    			urlString="http://"+urlString;
    	
    		//danach: Feld nicht leer und URL valide
    		ButtonMethods.setUri(urlString);
			
    		editText.setText(urlString);
			
			String date = ButtonMethods.getDate();
			String time = ButtonMethods.getTime();
			//Datenbank-Eintrag
			dataSource.open();
			dataSource.createEntry(date, time, urlString);
			dataSource.close();
			
			webview();
			
    	}
    	
    }
	
    public void webview(){
    	Intent i = new Intent (getApplicationContext(), WebActivity.class);
    	startActivity(i);
    }
    
	private void history(){
		Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
		startActivity(i);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		//wenn auf zurueckButton geklickt wird und man in WebContent ist
		if(keyCode == KeyEvent.KEYCODE_BACK){
			MainActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
