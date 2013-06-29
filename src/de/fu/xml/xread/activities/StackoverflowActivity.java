package de.fu.xml.xread.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import de.fu.xml.xread.R;

public class StackoverflowActivity extends XReadActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("StackoverflowActivity");
    	super.onCreate(savedInstanceState);
    	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		//wenn auf zurueckButton geklickt wird und man in WebContent ist
		if(keyCode == KeyEvent.KEYCODE_BACK){
			startIntent(MainActivity.class);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected Context GetContext() {
		return this;
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.stackoverflow;
	}

}
