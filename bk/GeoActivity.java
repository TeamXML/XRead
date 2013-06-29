package de.fu.xml.xread.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.activities.sqlAndHelper.ButtonMethods;

public class GeoActivity extends XReadActivity {
	
	EditText geoAddrText;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("GeoActivity");
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.geo);
    	
    	geoAddrText =  (EditText)findViewById(id.editTextAdresseGeo);
    	
//    	hideKeyboard();
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
		Editable searchText = geoAddrText.getText();
		if (searchText.length() > 0) {
			ButtonMethods.setUri("http://maps.googleapis.com/maps/api/geocode/xml?address="+ searchText +"&sensor=false");
			startActivity(new Intent(getApplicationContext(), WebActivity.class));
		}
		
	}
}
