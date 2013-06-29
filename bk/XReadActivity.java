package de.fu.xml.xread.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.os.Bundle;

public abstract class XReadActivity extends Activity{
	
	final Builder alert = new Builder(getApplicationContext());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
/*	
*/
}
