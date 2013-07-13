package de.fu.xml.xread.main.transformer;

import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import de.fu.xml.xread.R;

public class DefaultTransformer {

	Context _context;
	
	public DefaultTransformer(Context context) {
		_context = context;
	}

	/**
	 * author : Jaehwan Ji
	 */
	public StreamSource GetTemplate(){
		return new StreamSource(_context.getResources().openRawResource(R.raw.default_xsl));			
	}
}