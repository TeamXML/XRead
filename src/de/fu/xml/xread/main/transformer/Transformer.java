package de.fu.xml.xread.main.transformer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.transform.stream.StreamSource;

import android.content.Context;
import de.fu.xml.xread.main.XSLTTransformer;

public class Transformer {
	
	private GeoDataTransformer _geoDataTransformer;
	private DefaultTransformer _defaultTransFormer;
	
	public Transformer(Context context) {
		_geoDataTransformer = new GeoDataTransformer(context);
		_defaultTransFormer = new DefaultTransformer(context);
	}
	
	public String transformGeoData(StreamSource data) throws IOException {
		return XSLTTransformer.transform(data, _geoDataTransformer.GetTemplate());
	}
	
	public String transFormDefault(InputStream data) throws IOException {
		ArrayList<String> items = new ArrayList<String>();
		
		// Detect & store ITEMS from the XML file
		items = _defaultTransFormer.DetectXML(data);
		
		// Update XSLT & transform from XML to HTML
//		return XSLTTransformer.transform(new StreamSource(data), _defaultTransFormer.GetTemplate(items));
		return _defaultTransFormer.UpdateHTML(XSLTTransformer.transform(new StreamSource(data), _defaultTransFormer.GetTemplate(items)));
		
	}
}
