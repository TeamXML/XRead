package de.fu.xml.xread.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;

public class TwitterActivity extends XReadActivity {
	
	EditText username;
	Button follow;
	ListView trends;
	List<String> list = new ArrayList<String>();;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("TwitterActivity");
    	super.onCreate(savedInstanceState);
    	
    	//Steuerelemente holen
    	username = (EditText)findViewById(id.editTextTwitterUser);
    	follow = (Button)findViewById(id.buttonFollow);
    	trends = (ListView)findViewById(id.listViewTrends);
    	
    	//holt sich alle Trends von der Userpage (via JSON)
    	list = getTrends();
    	//schreibt alle gefundenen Trend-Strings in die listview
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		trends.setAdapter(adapter);
		trends.setClickable(false);
		
		username.addTextChangedListener(new TextWatcher() {
			 
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(username.getText().toString().length() == 0) follow.setEnabled(false);
				else follow.setEnabled(true);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if(username.getText().toString().length() == 0) follow.setEnabled(false);
				else follow.setEnabled(true);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(username.getText().toString().length() == 0) follow.setEnabled(false);
				else follow.setEnabled(true);
			}
		});
		
    	follow.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				checkUser(username.getText().toString());
			}
    	});
    	
    	
	}

	private List<String> getTrends() {
		//dummies
		list.add("#Merkel");
    	list.add("#Neuland");
    	list.add("#Obama");
    	list.add("#Berlin");
		return list;
	}
	
	/**
	 * 
	 * @param twitterUser: kontrolliert, ob User mit diesem Nickname ueberhaupt existiert
	 */
	private void checkUser(String twitterUser) {
		// TODO Auto-generated method stub
		
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
		return R.layout.twitter;
	}

}
