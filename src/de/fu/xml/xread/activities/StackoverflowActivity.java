package de.fu.xml.xread.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import de.fu.xml.xread.R;
import de.fu.xml.xread.R.id;
import de.fu.xml.xread.helper.EnablingTextWatcher;
import de.fu.xml.xread.helper.WebHelper;

public class StackoverflowActivity extends XReadActivity {
	
	private final String TAG = "StackoverflowActivity";
	
	EditText questionText;
	Button searchButton;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
    	super.onCreate(savedInstanceState);
    	
    	questionText = (EditText)findViewById(id.editTextFrageStackoverflow);
    	searchButton = (Button)findViewById(id.buttonSucheStackoverflow);
    	
    	questionText.addTextChangedListener(new EnablingTextWatcher(searchButton));
	}
	
	public void onSearchButtonClick(View view){
		hideKeyboard();
		Editable searchText = questionText.getText();
		if (questionText.length() > 0) {
			startWebSearch(WebHelper.getStackOverflowSearch(searchText.toString()));
		}
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
