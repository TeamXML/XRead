package de.fu.xml.xread.main.transformer;

import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import de.fu.xml.xread.R;

public class DefaultTransformer {

	Context _context;
	InputStream _xsl;
	
	public DefaultTransformer(Context context) {
		_context = context;
	}

	public void UpdateXSL(InputStream xml) {
		InputStream default_xsl = _context.getResources().openRawResource(R.raw.default_xsl);
	}

	public StreamSource GetTemplate() {
		return new StreamSource(_xsl);
	}

}
