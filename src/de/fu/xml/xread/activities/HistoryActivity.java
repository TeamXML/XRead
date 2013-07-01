package de.fu.xml.xread.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.fu.xml.xread.R;
import de.fu.xml.xread.helper.DateHelper;
import de.fu.xml.xread.helper.Entry;
import de.fu.xml.xread.helper.HistoryDataSource;
import de.fu.xml.xread.helper.WebHelper;

public class HistoryActivity extends XReadActivity {

	private List<Entry> list = new ArrayList<Entry>();
	private HistoryDataSource dataSource;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("HistoryActivity");
    	super.onCreate(savedInstanceState);
    	
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
				
				WebHelper.setUri(link);
				String date = DateHelper.getDate();
				String time = DateHelper.getTime();
				//Datenbank-Eintrag
				dataSource.open();
				dataSource.createEntry(date, time, WebHelper.getUri());
				dataSource.close();
				
				Intent i = new Intent(getApplicationContext(), WebActivity.class);
				startActivity(i);
			}
		
		});
	}
	
	@Override
	protected int getLayoutResourceId() {
		return R.layout.history;
	}
	
	@Override
	protected Context GetContext() {		
		return this;
	};
}
