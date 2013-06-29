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

public class GeoActivity extends XReadActivity {

	private final String TAG = "GeoActivity";

	EditText geoAddrText;
	Button searchAddrButton;
	EditText geoLatText;
	EditText geoLongText;
	Button searchLatLongButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setTitle(TAG);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.geo);

		geoAddrText = (EditText) findViewById(id.editTextAdresseGeo);
		searchAddrButton = (Button) findViewById(id.buttonAddrSearch);
		geoLatText = (EditText) findViewById(id.editTextLatitude);
		geoLongText = (EditText) findViewById(id.editTextLongitude);
		searchLatLongButton = (Button) findViewById(id.buttonLatLongSuche);

		geoAddrText.addTextChangedListener(new EnablingTextWatcher(
				searchAddrButton));

		geoLatText.addTextChangedListener(new EnablingTextWatcher(
				searchLatLongButton, geoLongText));
		geoLongText.addTextChangedListener(new EnablingTextWatcher(
				searchLatLongButton, geoLatText));
	}

	public void onGeoAddrSearchClick(View view) {
		hideKeyboard();
		Editable searchText = geoAddrText.getText();
		if (geoAddrText.length() > 0) {
			startWebSearch(WebHelper.getMapsAdressSearch(searchText.toString()));
		}
	}

	@Override
	protected Context GetContext() {
		return this;
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.geo;
	}
}
