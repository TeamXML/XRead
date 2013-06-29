package de.fu.xml.xread.activities;

import android.content.Context;
import android.os.Bundle;
import de.fu.xml.xread.R;

public class DBPediaActivity extends XReadActivity {
	private final String TAG = "DBPediaActivity";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
    	super.onCreate(savedInstanceState);
	}
	
	@Override
	protected Context GetContext() {
		return this;
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.dbpedia;
	}

}
