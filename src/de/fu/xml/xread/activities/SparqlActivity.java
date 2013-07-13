package de.fu.xml.xread.activities;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import de.fu.xml.xread.R;
import de.fu.xml.xread.helper.EnablingTextWatcher;
import de.fu.xml.xread.helper.WebHelper;

public class SparqlActivity extends XReadActivity {
	
	protected static final String TAG = "SparqlActivity";
	private EditText query;
	private Button button;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle(TAG);
    	super.onCreate(savedInstanceState);
    	
    	query = (EditText)findViewById(R.id.editTextQuery);
    	
    	button = (Button)findViewById(R.id.button4);
    	
    	query.addTextChangedListener(new EnablingTextWatcher(button));
    	
    	button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideKeyboard();
				Editable searchText = query.getText();
				if (searchText.length() > 0) {
					try {
						startWebSearch(WebHelper.getSPARQLSearch(searchText.toString()));
					} catch (UnsupportedEncodingException e) {
						handleError("Fehler beim Transformieren der Daten", e, TAG);
					}
				}
			}
		});
	}

	@Override
	protected Context GetContext() {
		return this;
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.sparql;
	}
}
