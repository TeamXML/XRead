package de.fu.xml.xread.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import de.fu.xml.xread.R;
import de.fu.xml.xread.helper.HistoryDataSource;
import de.fu.xml.xread.helper.WebHelper;

public class SparqlActivity extends Activity {
	
	private List<String> list = new ArrayList<String>();
	private HistoryDataSource dataSource;
	static String query;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("SparqlActivity");
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.sparql);
    	dataSource = new HistoryDataSource(this);
    	final EditText query1 = (EditText)findViewById(R.id.editTextQuery1);
    	final EditText query2 = (EditText)findViewById(R.id.editTextQuery2);
    	final EditText query3 = (EditText)findViewById(R.id.editTextQuery3);
    	
    	
    	Button button1 = (Button)findViewById(R.id.button1);
    	button1.setOnClickListener(new OnClickListener() {
    		
			@Override
			public void onClick(View v) {
				String tmp = "Pr�dikat";
				query1.setText(tmp);
				query1.setSelection(query1.getText().length());
			}
		});
    	
    	Button button2 = (Button)findViewById(R.id.button2);
    	button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tmp = "Subjekt";
				query2.setText(tmp);
				query2.setSelection(query2.getText().length());
			}
		});
    	
    	Button button3 = (Button)findViewById(R.id.button3);
    	button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tmp = "Objekt";
				query3.setText(tmp);
				query3.setSelection(query3.getText().length());
			}
		});
    	
    	Button button4 = (Button)findViewById(R.id.button4);
    	button4.setOnClickListener(new OnClickListener() {
    		
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "SparqlQuery wird abgeschickt!", Toast.LENGTH_LONG).show();
				String tmp = query1.getText() + " " + query2.getText() + " " + query3.getText();
				query = tmp;
			}
		});
    	
    	//f�llt Liste mit allen URIs
    	try {
			dataSource.open();
			list = dataSource.getOnlyAllLinks();
			dataSource.close();
		} catch (Exception e) {
			Toast.makeText(this, "Fehler beim Auslesen: "+e.toString(), Toast.LENGTH_LONG).show();
		}
    	
    	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
    	final Spinner spinner = (Spinner)findViewById(R.id.sparqlspinner);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String str1 = spinner.getSelectedItem().toString();
                WebHelper.setUri(str1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public String getQuery(){
		return query;
	}
	
	
	
}
