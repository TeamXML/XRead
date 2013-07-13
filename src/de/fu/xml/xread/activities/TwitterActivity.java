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

public class TwitterActivity extends XReadActivity {
	
	EditText username;
	Button follow;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.setTitle("TwitterActivity");
    	super.onCreate(savedInstanceState);
    	
    	//Steuerelemente holen
    	username = (EditText)findViewById(id.editTextTwitterUser);
    	follow = (Button)findViewById(id.buttonFollow);
    	
		username.addTextChangedListener(new EnablingTextWatcher(follow));
		
    	follow.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				hideKeyboard();
				Editable searchText = username.getText();
				if (searchText.length() > 0) {
					startWebSearch(WebHelper.getTwitterTimeLine(searchText.toString()));
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
		return R.layout.twitter;
	}

}
