package de.fu.xml.xread.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import de.fu.xml.xread.R;

public class GeoActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("GeoActivity");
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.geo);
    	
    	//Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	if(imm.isAcceptingText())
    		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		//wenn auf zurueckButton geklickt wird und man in WebContent ist
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void onGeoAddrSearchClick(View view){
		
	}

}
