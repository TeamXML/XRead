package de.fu.xml.xread.main;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Wenn auf das Textfeld geklickt wird, wird Text markiert und kann überschrieben werden */
    public void EditTextURI_Click(View view){
		//Tastatur öffnet sich
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		EditText uri = (EditText)findViewById(id.editTextURI);
	
		
		
		//schließt Tastatur
		InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		
	}
    
    /** Wenn auf Button Cancel geklickt wird, dann soll der aktuelle Prozess abgebrochen werden */
    public void Button_Cancel(View view){
    	
    }
    
    /** Wenn auf Button Refresh geklickt wird, dann soll der aktuelle Prozess abgebrochen und neu gestartet werden*/
    public void Button_Refresh(View view){
    	
    }
    
    /** Wenn auf Button Play geklickt wird, dann beginnt der Prozess des Parsens */
    public void Button_Play(View view){
    	
    }
}
