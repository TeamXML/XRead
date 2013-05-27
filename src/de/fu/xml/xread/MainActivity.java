package de.fu.xml.xread;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Aufgabe: Autovervollständigung für textfeld mit den Inhalten, die in einem Array von Histories stehen

    }


    /** Handler, wenn auf Button geklickt wird 
     *  Achtung: in Layout muss Methodenname verankert sein!*/
    public void onButtonClick(View view){
    	switch (view.getId()) {
        	case id.cancelButton:
        		Button_Cancel();
        		break;
        	case id.refreshButton:
        		Button_Refresh();
        		break;
        	case id.startButton:
        		Button_Play();
        		break;
        	default:
                Log.e("MainActivity.java", "Kein Button mit der ID: " + view.getId() + " vorhanden.");
                break;
    	}
           
    }
    
    /** Wenn auf Button Cancel geklickt wird, dann soll der aktuelle Prozess abgebrochen werden */
    public void Button_Cancel(){
    	Toast toast = Toast.makeText(this, "Vorgang abgebrochen!", Toast.LENGTH_SHORT);
    	toast.show();
    	System.out.println("Es wurde auf Button_Cancel geklickt!");
    }
    
    /** Wenn auf Button Refresh geklickt wird, dann soll der aktuelle Prozess abgebrochen und neu gestartet werden*/
    public void Button_Refresh(){
    	Toast toast = Toast.makeText(getBaseContext(), "Vorgang Neuversuch!", Toast.LENGTH_SHORT);
    	toast.show();
    	System.out.println("Es wurde auf Button_Cancel geklickt!");
    }
    
    /** Wenn auf Button Play geklickt wird, dann beginnt der Prozess des Parsens */
    public void Button_Play(){
    	Toast toast = Toast.makeText(getBaseContext(), "Starte Download ...", Toast.LENGTH_SHORT);
    	toast.show();
    	//String uri = textField.toString();
    	
    	//Datenbank-Eintrag der URI
//		dataSource.open();
//		dataSource.createEntry(uri);
//		Toast toast2 = Toast.makeText(getBaseContext(), "String eingetragen ...", Toast.LENGTH_SHORT);
//    	toast2.show();
//		dataSource.close();
		
		//Tastatur ausblenden	
		InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			
    }
}
