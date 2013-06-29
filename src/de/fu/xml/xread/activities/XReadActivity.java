package de.fu.xml.xread.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public abstract class XReadActivity extends Activity {
	
	Builder alert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResourceId());
		alert = new Builder(GetContext());
	}

	protected abstract Context GetContext();

	protected abstract int getLayoutResourceId();

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
