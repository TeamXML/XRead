package de.fu.xml.xread.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import de.fu.xml.xread.R;
import de.fu.xml.xread.helper.EnablingTextWatcher;
import de.fu.xml.xread.helper.WebHelper;

public class DBPediaActivity extends XReadActivity {
	private final String TAG = "DBPediaActivity";
	
	private EditText _searchText;
	private Button _button;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
    	super.onCreate(savedInstanceState);
    	
    	_searchText = (EditText) findViewById(R.id.editTextFragedbpedia);
    	_button = (Button) findViewById(R.id.buttonSuchedbpedia);
    	
    	_searchText.addTextChangedListener(new EnablingTextWatcher(_button));
	}
	
	public void dbPediaButtonOnClick(View view){
		hideKeyboard();
		Editable searchText = _searchText.getText();
		if (_searchText.length() > 0) {
			startWebSearch(WebHelper.getDBPediaSearch(searchText.toString()));
		}
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
