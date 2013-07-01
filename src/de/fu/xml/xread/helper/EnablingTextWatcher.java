package de.fu.xml.xread.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class EnablingTextWatcher implements TextWatcher {

	View _button;
	TextView[] _dependencies;

	public EnablingTextWatcher(View button, TextView... dependencies) {
		_button = button;
		_dependencies = dependencies;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// Do Nothing
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//Do Nothing
	}

	@Override
	public void afterTextChanged(Editable s) {
		boolean dependenciesEnabled = true;
		
		for (TextView dependency : _dependencies) {
			dependenciesEnabled &= dependency.getText().length() > 0;
		}
		
		_button.setEnabled(s.length() > 0 && dependenciesEnabled);
	}

}
