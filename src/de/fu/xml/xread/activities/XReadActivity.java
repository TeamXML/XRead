package de.fu.xml.xread.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import de.fu.xml.xread.helper.WebHelper;

public abstract class XReadActivity extends Activity {
	
	Builder alert;
	
	private static boolean _isInitialized;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResourceId());
		alert = new Builder(GetContext());
		
		if (!_isInitialized){
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
			
			String serverAdress = preferences.getString("serverAdress", "10.0.2.2:8080");
			WebHelper.SetServerAdress(serverAdress);
			_isInitialized = true;
		}
	}

	protected abstract Context GetContext();

	protected abstract int getLayoutResourceId();

	protected void startWebSearch(String uri) {
		WebHelper.setUri(uri);
		startActivity(new Intent(getApplicationContext(), WebActivity.class));
	}
	
	protected void hideKeyboard() {
		// Falls Keyboard aufgeklappt ist, dann wieder zuklappen.
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isAcceptingText())
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	protected void showToast(CharSequence text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
	}
	
	protected void startIntent(Class<?> classToStart) {
		Intent i = new Intent(getApplicationContext(), classToStart);
		startActivity(i);
	}
	
	protected void handleError(String description, String tag) {
		Log.e(tag, description);
		showAlert(description);
	}

	protected void handleError(String description, Exception e, String tag) {
		Log.e(tag, description, e);
		showAlert(description);
	}

	private void showAlert(String description) {
		alert.setTitle("Fehler");
		alert.setMessage(description);

		alert.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
		alert.show();
	}

}
