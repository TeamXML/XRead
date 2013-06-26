package de.fu.xml.xread.activities;

import java.util.ArrayList;
import java.util.List;

import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.R.layout;
import de.fu.xml.xread.activities.sqlAndHelper.ButtonMethods;
import de.fu.xml.xread.activities.sqlAndHelper.Entry;
import de.fu.xml.xread.activities.sqlAndHelper.HistoryDataSource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HistoryActivity extends Activity {

	private List<Entry> list = new ArrayList<Entry>();
	private HistoryDataSource dataSource;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("HistoryActivity");
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.history);
    	dataSource = new HistoryDataSource(this);
    	
    	history();
	}
	
	/** Oeffnet View, wo alle URIs gelistet sind, die aufgerufen sind (mit TimeStamp)*/ 
	public void history(){
		
		try {
			dataSource.open();
			list = dataSource.getAllEntries();
			dataSource.close();
		} catch (Exception e) {
			Toast.makeText(this, "Fehler beim Auslesen: "+e.toString(), Toast.LENGTH_LONG).show();
		}
		
		ArrayAdapter<Entry> adapter = new ArrayAdapter<Entry>(this, android.R.layout.simple_list_item_1, list);
		final ListView lview = (ListView)findViewById(R.id.listView1);
		lview.setAdapter(adapter);
		lview.setClickable(true);
		lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String itemString = lview.getItemAtPosition(arg2).toString();
				
				String[] list = itemString.split(" ");
				String link = list[1];
				link = link.replace("besucht", "");
				link = link.replace("\n", "");
				
				ButtonMethods.setUri(link);
				String date = ButtonMethods.getDate();
				String time = ButtonMethods.getTime();
				//Datenbank-Eintrag
				dataSource.open();
				dataSource.createEntry(date, time, ButtonMethods.getUri());
				dataSource.close();
				
				Intent i = new Intent(getApplicationContext(), WebActivity.class);
				startActivity(i);
			}
		
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		//wenn auf zurueckButton geklickt wird und man in History ist
		if(keyCode == KeyEvent.KEYCODE_BACK && ButtonMethods.getMainIsOpen()){
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);
			return true;
		}
		
		if(keyCode == KeyEvent.KEYCODE_BACK && ButtonMethods.getWebIsOpen()){
			Intent i = new Intent(getApplicationContext(), WebActivity.class);
			startActivity(i);
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
